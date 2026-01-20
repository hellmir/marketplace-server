package com.personal.marketnote.user.adapter.out.web.reward;

import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.common.exception.RewardServiceRequestFailedException;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.port.out.reward.ModifyUserPointPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static com.personal.marketnote.common.utility.AccrualPointAmountConstant.REFERRED_USER_POINT_AMOUNT;
import static com.personal.marketnote.common.utility.AccrualPointAmountConstant.REFERRER_USER_POINT_AMOUNT;
import static com.personal.marketnote.common.utility.ApiConstant.INTER_SERVER_MAX_REQUEST_COUNT;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class RewardServiceClient implements ModifyUserPointPort {
    @Value("${reward-service.base-url}")
    private String rewardServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate;

    @Override
    public void registerUserPoint(Long userId, String userKey) {
        if (FormatValidator.hasNoValue(userId) || FormatValidator.hasNoValue(userKey)) {
            return;
        }

        URI uri = buildRegisterUserPointUri(userId, userKey);
        HttpHeaders headers = buildHeaders();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Void.class);

                if (FormatValidator.hasValue(response) && response.getStatusCode().is2xxSuccessful()) {
                    return;
                }

                HttpStatusCode statusCode = null;
                if (FormatValidator.hasValue(response)) {
                    statusCode = response.getStatusCode();
                }

                log.warn(
                        "Reward service responded with non-2xx status for userId={}, status={}",
                        userId, statusCode
                );
            } catch (Exception e) {
                log.warn(
                        "Failed to register user point on reward-service: userId={}, attempt={}, message={}",
                        userId, i + 1, e.getMessage(), e
                );
            }

            sleep(1000);
        }

        throw new RewardServiceRequestFailedException(new IOException());
    }

    @Override
    public void accrueReferralPoints(Long referrerUserId, Long referredUserId) {
        accrueUserPoint(
                referrerUserId,
                REFERRER_USER_POINT_AMOUNT,
                "추천인 코드 등록 적립"
        );

        accrueUserPoint(
                referredUserId,
                REFERRED_USER_POINT_AMOUNT,
                "신규 회원 초대 적립"
        );
    }

    private void accrueUserPoint(Long userId, long amount, String reason) {
        if (FormatValidator.hasNoValue(userId) || amount <= 0) {
            return;
        }

        URI uri = buildUserPointUri(userId);
        HttpHeaders headers = buildHeaders();

        ensureUserPointExists(uri, headers, userId);

        ModifyUserPointRequest request = ModifyUserPointRequest.from(userId, amount, reason);
        HttpEntity<ModifyUserPointRequest> httpEntity = new HttpEntity<>(request, headers);

        sendModifyRequest(uri, httpEntity, userId, amount);
    }

    private void ensureUserPointExists(URI uri, HttpHeaders headers, Long userId) {
        try {
            restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), Void.class);
        } catch (Exception e) {
            log.info("User point registration skipped: userId={}, message={}", userId, e.getMessage());
        }
    }

    private void sendModifyRequest(
            URI uri,
            HttpEntity<ModifyUserPointRequest> httpEntity,
            Long userId,
            long amount
    ) {
        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                ResponseEntity<Void> response = restTemplate.exchange(uri, HttpMethod.PATCH, httpEntity, Void.class);

                if (FormatValidator.hasValue(response) && response.getStatusCode().is2xxSuccessful()) {
                    return;
                }

                HttpStatusCode statusCode = null;
                if (FormatValidator.hasValue(response)) {
                    statusCode = response.getStatusCode();
                }

                log.warn(
                        "Reward service responded with non-2xx status for userId={}, amount={}, status={}",
                        userId, amount, statusCode
                );
            } catch (Exception e) {
                log.warn(
                        "Failed to accrue user point on reward-service: userId={}, amount={}, attempt={}, message={}",
                        userId, amount, i + 1, e.getMessage(), e
                );
            }

            sleep(1000);
        }

        throw new RewardServiceRequestFailedException(new IOException());
    }

    private URI buildUserPointUri(Long userId) {
        return UriComponentsBuilder
                .fromUriString(rewardServiceBaseUrl)
                .path("/api/v1/users/{userId}/points")
                .buildAndExpand(userId)
                .toUri();
    }

    private URI buildRegisterUserPointUri(Long userId, String userKey) {
        return UriComponentsBuilder
                .fromUriString(rewardServiceBaseUrl)
                .path("/api/v1/users/{userId}/points")
                .queryParam("userKey", userKey)
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
        private static final String CHANGE_TYPE_ACCRUAL = "ACCRUAL";
        private static final String SOURCE_TYPE_USER = "USER";

        private static ModifyUserPointRequest from(Long userId, long amount, String reason) {
            return new ModifyUserPointRequest(
                    CHANGE_TYPE_ACCRUAL,
                    Math.abs(amount),
                    SOURCE_TYPE_USER,
                    userId,
                    reason
            );
        }
    }
}
