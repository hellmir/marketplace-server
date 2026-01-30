package com.personal.marketnote.product.adapter.out.web.commerce;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.adapter.in.request.RegisterInventoryRequest;
import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.common.exception.CommerceServiceRequestFailedException;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.response.GetInventoriesResponse;
import com.personal.marketnote.product.port.out.inventory.FindStockPort;
import com.personal.marketnote.product.port.out.inventory.RegisterInventoryPort;
import com.personal.marketnote.product.port.out.result.GetInventoryResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.personal.marketnote.common.utility.ApiConstant.*;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class CommerceServiceClient implements RegisterInventoryPort, FindStockPort {
    @Value("${commerce-service.base-url}")
    private String commerceServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate;

    @Override
    public void registerInventory(Long pricePolicyId) {
        URI uri = UriComponentsBuilder
                .fromUriString(commerceServiceBaseUrl)
                .path("/api/v1/inventories")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        HttpEntity<RegisterInventoryRequest> httpEntity
                = new HttpEntity<>(new RegisterInventoryRequest(pricePolicyId), headers);

        sendRequest(uri, httpEntity);
    }

    public void sendRequest(URI uri, HttpEntity<RegisterInventoryRequest> httpEntity) {
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        Exception error = new Exception();

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                restTemplate.postForEntity(uri, httpEntity, Void.class);
                return;
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }

                try {
                    // 대상 서비스 장애 시 요청 트래픽 폭주를 방지하기 위해 jitter 설정
                    long jitteredSleepMillis = ThreadLocalRandom.current()
                            .nextLong(Math.max(1L, sleepMillis) + 1);
                    Thread.sleep(jitteredSleepMillis);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return;
                }

                // exponential backoff 적용
                sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
            }
        }

        log.error("Failed to register inventory: {} with error: {}", uri, error.getMessage(), error);
        throw new CommerceServiceRequestFailedException(new IOException());
    }

    @Override
    public Set<GetInventoryResult> findByPricePolicyIds(List<Long> pricePolicyIds) {
        URI uri = UriComponentsBuilder
                .fromUriString(commerceServiceBaseUrl)
                .path("/api/v1/inventories")
                .queryParam("pricePolicyIds", pricePolicyIds)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);

        try {
            return sendRequest(uri, headers, pricePolicyIds).inventories();
        } catch (CommerceServiceRequestFailedException csrfe) {
            return pricePolicyIds.stream()
                    .map(GetInventoryResult::generateResultWithoutStock)
                    .collect(Collectors.toSet());
        }
    }

    public GetInventoriesResponse sendRequest(URI uri, HttpHeaders headers, List<Long> pricePolicyIds) {
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        Exception error = new Exception();

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                BaseResponse<GetInventoriesResponse> response =
                        restTemplate.exchange(
                                uri,
                                HttpMethod.GET,
                                new HttpEntity<>(headers),
                                new ParameterizedTypeReference<BaseResponse<GetInventoriesResponse>>() {
                                }
                        ).getBody();

                if (FormatValidator.hasNoValue(response)) {
                    throw new CommerceServiceRequestFailedException(new IOException());
                }

                GetInventoriesResponse getInventoriesResponse = response.getContent();
                return FormatValidator.hasValue(getInventoriesResponse)
                        ? getInventoriesResponse
                        : new GetInventoriesResponse(new HashSet<>());
            } catch (Exception e) {
                log.warn(e.getMessage(), e);

                try {
                    // 대상 서비스 장애 시 요청 트래픽 폭주를 방지하기 위해 jitter 설정
                    long jitteredSleepMillis = ThreadLocalRandom.current()
                            .nextLong(Math.max(1L, INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND) + 1);
                    Thread.sleep(jitteredSleepMillis);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        log.error("Failed to get inventories: {} with error: {}", uri, error.getMessage(), error);
        throw new CommerceServiceRequestFailedException(new IOException());
    }
}
