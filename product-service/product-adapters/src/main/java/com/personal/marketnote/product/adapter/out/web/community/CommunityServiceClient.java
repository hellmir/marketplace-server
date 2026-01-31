package com.personal.marketnote.product.adapter.out.web.community;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.response.GetProductReviewAggregatesResponse;
import com.personal.marketnote.product.adapter.out.response.ProductReviewAggregateItemResponse;
import com.personal.marketnote.product.port.out.result.ProductReviewAggregateResult;
import com.personal.marketnote.product.port.out.review.FindProductReviewAggregatesPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.personal.marketnote.common.utility.ApiConstant.INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
import static com.personal.marketnote.common.utility.ApiConstant.INTER_SERVER_MAX_REQUEST_COUNT;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class CommunityServiceClient implements FindProductReviewAggregatesPort {
    @Value("${community-service.base-url}")
    private String communityServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate;

    @Override
    public Map<Long, ProductReviewAggregateResult> findByProductIds(List<Long> productIds) {
        if (FormatValidator.hasNoValue(productIds)) {
            return Map.of();
        }

        URI uri = UriComponentsBuilder
                .fromUriString(communityServiceBaseUrl)
                .path("/api/v1/products/review-aggregates")
                .queryParam("productIds", productIds)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);

        return sendRequest(uri, new HttpEntity<>(headers));
    }

    public Map<Long, ProductReviewAggregateResult> sendRequest(URI uri, HttpEntity<Void> httpEntity) {
        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                BaseResponse<GetProductReviewAggregatesResponse> response =
                        restTemplate.exchange(
                                uri,
                                HttpMethod.GET,
                                httpEntity,
                                new ParameterizedTypeReference<BaseResponse<GetProductReviewAggregatesResponse>>() {
                                }
                        ).getBody();

                if (response == null || response.getContent() == null) {
                    return Map.of();
                }

                List<ProductReviewAggregateItemResponse> reviewAggregates
                        = response.getContent().reviewAggregates();
                if (FormatValidator.hasNoValue(reviewAggregates)) {
                    return Map.of();
                }

                return reviewAggregates.stream()
                        .filter(Objects::nonNull)
                        .filter(item -> item.productId() != null)
                        .collect(Collectors.toMap(
                                ProductReviewAggregateItemResponse::productId,
                                item -> new ProductReviewAggregateResult(
                                        item.productId(),
                                        item.totalCount(),
                                        item.averageRating()
                                ),
                                (existing, replacement) -> existing
                        ));
            } catch (Exception e) {
                log.warn(e.getMessage(), e);

                try {
                    // 대상 서비스 장애 시 요청 트래픽 폭주를 방지하기 위해 jitter 설정
                    long jitteredSleepMillis = ThreadLocalRandom.current()
                            .nextLong(Math.max(1L, INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND) + 1);
                    Thread.sleep(jitteredSleepMillis);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return Map.of();
                }
            }
        }

        log.error("Failed to get product review aggregates: {}", uri);
        return Map.of();
    }
}
