package com.personal.marketnote.commerce.adapter.out.web.reward;

import com.personal.marketnote.commerce.port.out.reward.ModifyUserPointPort;
import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.common.exception.RewardServiceRequestFailedException;
import com.personal.marketnote.common.utility.FormatValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import static com.personal.marketnote.common.utility.ApiConstant.*;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class RewardServiceClient implements ModifyUserPointPort {
    private static final String SOURCE_TYPE_USER = "USER";
    private static final String CHANGE_TYPE_ACCRUAL = "ACCRUAL";
    private static final String SHARE_PURCHASE_REASON = "링크 공유 회원 상품 구매";

    @Value("${reward-service.base-url}")
    private String rewardServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    @Value("${reward-service.share-point-amount:5000}")
    private long sharePointAmount;

    private final RestTemplate restTemplate;

    @Override
    public void accrueSharedPurchasePoints(List<Long> sharerIds) {
        if (FormatValidator.hasNoValue(sharerIds)) {
            return;
        }

        sharerIds.stream()
                .filter(Objects::nonNull)
                .forEach(this::accrueSharerPoint);
    }

    private void accrueSharerPoint(Long sharerId) {
        if (sharePointAmount <= 0) {
            return;
        }

        URI uri = buildUserPointUri(sharerId);
        HttpHeaders headers = buildHeaders();

        ensureUserPointExists(uri, headers, sharerId);

        ModifyUserPointRequest request = ModifyUserPointRequest.of(sharePointAmount, sharerId);
        HttpEntity<ModifyUserPointRequest> httpEntity = new HttpEntity<>(request, headers);

        sendRequest(uri, httpEntity, sharerId);
    }

    private void ensureUserPointExists(URI uri, HttpHeaders headers, Long userId) {
        try {
            restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), Void.class);
        } catch (Exception e) {
            log.info("User point registration skipped: userId={}, message={}", userId, e.getMessage());
        }
    }

    private void sendRequest(URI uri, HttpEntity<ModifyUserPointRequest> httpEntity, Long userId) {
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        Exception error = new Exception();

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.PATCH, httpEntity, Void.class);
                if (FormatValidator.hasValue(response) && response.getStatusCode().is2xxSuccessful()) {
                    return;
                }

                log.warn(
                        "Reward service responded with non-2xx status for userId={}, status={}",
                        userId, response.getStatusCode()
                );
            } catch (Exception e) {
                log.warn(
                        "Failed to accrue user point on reward-service: userId={}, attempt={}, message={}",
                        userId, i + 1, e.getMessage(), e
                );
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }
            }

            sleep(sleepMillis);
            sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
        }

        log.error("Failed to accrue user point: {} with error: {}", uri, error.getMessage(), error);
        throw new RewardServiceRequestFailedException(new IOException());
    }

    private URI buildUserPointUri(Long userId) {
        return UriComponentsBuilder
                .fromUriString(rewardServiceBaseUrl)
                .path("/api/v1/users/{userId}/points")
                .buildAndExpand(userId)
                .toUri();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private record ModifyUserPointRequest(
            String changeType,
            long amount,
            String sourceType,
            Long sourceId,
            String reason
    ) {
        private static ModifyUserPointRequest of(long amount, Long sourceId) {
            return new ModifyUserPointRequest(
                    CHANGE_TYPE_ACCRUAL,
                    Math.abs(amount),
                    SOURCE_TYPE_USER,
                    sourceId,
                    SHARE_PURCHASE_REASON
            );
        }
    }
}
