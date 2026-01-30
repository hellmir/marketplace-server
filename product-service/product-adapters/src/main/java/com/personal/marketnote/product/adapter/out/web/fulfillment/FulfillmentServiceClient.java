package com.personal.marketnote.product.adapter.out.web.fulfillment;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.common.exception.FulfillmentServiceRequestFailedException;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.web.fulfillment.request.RegisterFasstoGoodsItemRequest;
import com.personal.marketnote.product.adapter.out.web.fulfillment.response.FasstoAuthTokenResponse;
import com.personal.marketnote.product.adapter.out.web.fulfillment.response.RegisterFasstoGoodsResponse;
import com.personal.marketnote.product.port.out.fulfillment.RegisterFulfillmentVendorGoodsCommand;
import com.personal.marketnote.product.port.out.fulfillment.RegisterFulfillmentVendorGoodsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.personal.marketnote.common.utility.ApiConstant.*;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class FulfillmentServiceClient implements RegisterFulfillmentVendorGoodsPort {
    @Value("${fulfillment-service.base-url}")
    private String fulfillmentServiceBaseUrl;

    @Value("${fulfillment-service.fassto.customer-code}")
    private String fulfillmentVendorCustomerCode;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate;

    @Override
    public void registerFulfillmentVendorGoods(RegisterFulfillmentVendorGoodsCommand command) {
        String fulfillmentVendorAccessToken = requestFulfillmentVendorAccessToken();
        if (FormatValidator.hasNoValue(fulfillmentVendorCustomerCode) || FormatValidator.hasNoValue(fulfillmentVendorAccessToken)) {
            throw new FulfillmentServiceRequestFailedException(new IOException());
        }

        URI uri = UriComponentsBuilder
                .fromUriString(fulfillmentServiceBaseUrl)
                .path("/api/v1/vendors/fassto/goods/{customerCode}")
                .buildAndExpand(fulfillmentVendorCustomerCode)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        headers.add("accessToken", fulfillmentVendorAccessToken);

        List<RegisterFasstoGoodsItemRequest> payload = List.of(RegisterFasstoGoodsItemRequest.from(command));
        HttpEntity<List<RegisterFasstoGoodsItemRequest>> httpEntity = new HttpEntity<>(payload, headers);

        sendRequest(uri, httpEntity, command);
    }

    private String requestFulfillmentVendorAccessToken() {
        URI uri = UriComponentsBuilder
                .fromUriString(fulfillmentServiceBaseUrl)
                .path("/api/v1/vendors/fassto/auth")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        Exception error = new Exception();

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                ResponseEntity<BaseResponse<FasstoAuthTokenResponse>> responseEntity =
                        restTemplate.exchange(
                                uri,
                                HttpMethod.POST,
                                httpEntity,
                                new ParameterizedTypeReference<BaseResponse<FasstoAuthTokenResponse>>() {
                                }
                        );

                if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                    throw new FulfillmentServiceRequestFailedException(new IOException());
                }

                BaseResponse<FasstoAuthTokenResponse> response = responseEntity.getBody();
                if (response == null || response.getContent() == null || response.getContent().tokenInfo() == null) {
                    throw new FulfillmentServiceRequestFailedException(new IOException());
                }

                String accessToken = response.getContent().tokenInfo().accessToken();
                if (FormatValidator.hasNoValue(accessToken)) {
                    throw new FulfillmentServiceRequestFailedException(new IOException());
                }

                return accessToken;
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
                    return null;
                }

                // exponential backoff 적용
                sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
            }
        }

        log.error("Failed to request fassto access token with error: {}", error.getMessage(), error);
        throw new FulfillmentServiceRequestFailedException(new IOException());
    }

    private void sendRequest(
            URI uri,
            HttpEntity<List<RegisterFasstoGoodsItemRequest>> httpEntity,
            RegisterFulfillmentVendorGoodsCommand command
    ) {
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        Exception error = new Exception();

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                ResponseEntity<BaseResponse<RegisterFasstoGoodsResponse>> responseEntity =
                        restTemplate.exchange(
                                uri,
                                HttpMethod.POST,
                                httpEntity,
                                new ParameterizedTypeReference<BaseResponse<RegisterFasstoGoodsResponse>>() {
                                }
                        );

                if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                    throw new FulfillmentServiceRequestFailedException(new IOException());
                }

                BaseResponse<RegisterFasstoGoodsResponse> response = responseEntity.getBody();
                if (FormatValidator.hasNoValue(response) || FormatValidator.hasNoValue(response.getContent())) {
                    throw new FulfillmentServiceRequestFailedException(new IOException());
                }

                RegisterFasstoGoodsResponse content = response.getContent();
                if (!content.isSuccess()) {
                    throw new FulfillmentServiceRequestFailedException(new IOException());
                }

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

        log.error("Failed to register fassto goods: {} with error: {}", command.cstGodCd(), error.getMessage(), error);
        throw new FulfillmentServiceRequestFailedException(new IOException());
    }
}
