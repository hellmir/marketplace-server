package com.personal.marketnote.community.adapter.out.web.commerce;

import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.community.port.out.order.UpdateOrderProductReviewStatusPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.personal.marketnote.common.utility.ApiConstant.INTER_SERVER_MAX_REQUEST_COUNT;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class CommerceServiceClient implements UpdateOrderProductReviewStatusPort {
    @Value("${commerce-service.base-url}")
    private String commerceServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate;

    @Override
    public void update(Long orderId, Long pricePolicyId, boolean isReviewed) {
        URI uri = UriComponentsBuilder
                .fromUriString(commerceServiceBaseUrl)
                .path("/api/v1/orders/{orderId}/order-products/{pricePolicyId}/review")
                .queryParam("isReviewed", isReviewed)
                .build()
                .expand(orderId, pricePolicyId)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        sendRequest(uri, httpEntity);
    }

    public void sendRequest(URI uri, HttpEntity<Void> httpEntity) {
        Exception error = new Exception();

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                restTemplate.exchange(uri, HttpMethod.PATCH, httpEntity, Void.class);
                return;
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        log.error("Failed to update order product review status: {} with error: {}", uri, error.getMessage(), error);
    }
}
