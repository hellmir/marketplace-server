package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.common.adapter.out.VendorAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response.FasstoAuthHeaderResponse;
import com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response.FasstoAuthResponse;
import com.personal.marketnote.fulfillment.configuration.FasstoAuthProperties;
import com.personal.marketnote.fulfillment.domain.vendor.FasstoAccessToken;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationTargetType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorName;
import com.personal.marketnote.fulfillment.exception.FasstoAuthRequestFailedException;
import com.personal.marketnote.fulfillment.port.out.vendor.RequestFasstoAuthPort;
import com.personal.marketnote.fulfillment.utility.VendorCommunicationFailureHandler;
import com.personal.marketnote.fulfillment.utility.VendorCommunicationPayloadGenerator;
import com.personal.marketnote.fulfillment.utility.VendorCommunicationRecorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.personal.marketnote.common.utility.ApiConstant.*;

@VendorAdapter
@RequiredArgsConstructor
@Slf4j
public class FasstoAuthClient implements RequestFasstoAuthPort {
    private static final String API_CD_PARAM = "apiCd";
    private static final String API_KEY_PARAM = "apiKey";

    private final RestTemplate restTemplate;
    private final FasstoAuthProperties properties;
    private final VendorCommunicationRecorder vendorCommunicationRecorder;
    private final VendorCommunicationPayloadGenerator vendorCommunicationPayloadGenerator;
    private final VendorCommunicationFailureHandler vendorCommunicationFailureHandler;

    @Override
    public FasstoAccessToken requestAccessToken() {
        URI uri = buildAuthUri();
        HttpEntity<Void> request = new HttpEntity<>(buildHeaders());

        Exception error = new Exception();
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        FulfillmentVendorCommunicationTargetType targetType = FulfillmentVendorCommunicationTargetType.AUTHENTICATION;
        FulfillmentVendorName vendorName = FulfillmentVendorName.FASSTO;

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            int attempt = i + 1;
            JsonNode requestPayloadJson = buildRequestPayloadJson(attempt);
            String requestPayload = requestPayloadJson.toString();

            try {
                ResponseEntity<FasstoAuthResponse> response = restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        request,
                        FasstoAuthResponse.class
                );

                try {
                    vendorCommunicationRecorder.record(
                            targetType,
                            FulfillmentVendorCommunicationType.REQUEST,
                            null,
                            vendorName,
                            requestPayload,
                            requestPayloadJson
                    );
                } catch (Exception recordingException) {
                    log.warn(
                            "Failed to record Fassto auth: attempt={}, message={}",
                            attempt,
                            recordingException.getMessage(),
                            recordingException
                    );
                }

                JsonNode responsePayloadJson = buildResponsePayloadJson(response, attempt);

                try {
                    vendorCommunicationRecorder.record(
                            targetType,
                            FulfillmentVendorCommunicationType.RESPONSE,
                            null,
                            vendorName,
                            responsePayloadJson.toString(),
                            responsePayloadJson
                    );
                } catch (Exception recordingException) {
                    log.warn(
                            "Failed to record Fassto auth: attempt={}, message={}",
                            attempt,
                            recordingException.getMessage(),
                            recordingException
                    );
                }

                FasstoAccessToken accessToken = parseResponse(response);

                if (FormatValidator.hasValue(accessToken)) {
                    return accessToken;
                }
            } catch (Exception e) {
                Map<String, Object> errorPayload = new LinkedHashMap<>();
                errorPayload.put("error", e.getClass().getSimpleName());
                errorPayload.put("message", e.getMessage());
                errorPayload.put("attempt", attempt);

                try {
                    vendorCommunicationFailureHandler.handleFailure(
                            targetType,
                            vendorName,
                            requestPayload,
                            requestPayloadJson,
                            errorPayload,
                            e
                    );
                } catch (Exception recordingException) {
                    log.error(
                            "Failed to handle Fassto auth failure: attempt={}, message={}",
                            attempt,
                            recordingException.getMessage(),
                            recordingException
                    );
                }

                log.warn("Failed to request Fassto auth: attempt={}, message={}", attempt, e.getMessage(), e);
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }
            }

            sleep(sleepMillis);
            sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
        }

        log.error("Failed to request Fassto auth: {} with error: {}", uri, error.getMessage(), error);
        throw new FasstoAuthRequestFailedException(new IOException(error));
    }

    private URI buildAuthUri() {
        validateProperties();
        return UriComponentsBuilder.fromUriString(properties.getBaseUrl())
                .path(properties.getConnectPath())
                .queryParam(API_CD_PARAM, properties.getApiCd())
                .queryParam(API_KEY_PARAM, properties.getApiKey())
                .build(true)
                .toUri();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private void validateProperties() {
        if (FormatValidator.hasNoValue(properties.getBaseUrl())) {
            throw new IllegalStateException("Fassto base URL is required.");
        }
        if (FormatValidator.hasNoValue(properties.getApiCd())) {
            throw new IllegalStateException("Fassto apiCd is required.");
        }
        if (FormatValidator.hasNoValue(properties.getApiKey())) {
            throw new IllegalStateException("Fassto apiKey is required.");
        }
        if (FormatValidator.hasNoValue(properties.getConnectPath())) {
            throw new IllegalStateException("Fassto auth connect path is required.");
        }
    }

    private FasstoAccessToken parseResponse(ResponseEntity<FasstoAuthResponse> response) {
        if (FormatValidator.hasNoValue(response)) {
            log.warn("Fassto auth response is null.");
            return null;
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.warn("Fassto auth returned non-2xx status: {}", response.getStatusCode());
            return null;
        }

        FasstoAuthResponse body = response.getBody();
        if (FormatValidator.hasNoValue(body) || !body.isSuccess()) {
            log.warn("Fassto auth returned failure: {}", formatError(body));
            return null;
        }

        if (
                FormatValidator.hasNoValue(body.data())
                        || FormatValidator.hasNoValue(body.data().accessToken())
                        || FormatValidator.hasNoValue(body.data().expreDatetime())
        ) {
            log.warn("Fassto auth response is missing token data.");
            return null;
        }

        return FasstoAccessToken.of(body.data().accessToken(), body.data().expreDatetime());
    }

    private String formatError(FasstoAuthResponse response) {
        if (FormatValidator.hasNoValue(response)) {
            return "no response body";
        }

        FasstoAuthHeaderResponse header = response.header();
        String code = FormatValidator.hasValue(header) ? header.code() : null;
        String message = FormatValidator.hasValue(header) ? header.msg() : null;
        String errorInfo = FormatValidator.hasValue(response.errorInfo()) ? response.errorInfo().toString() : null;

        return String.format("code=%s, message=%s, errorInfo=%s", code, message, errorInfo);
    }

    private JsonNode buildRequestPayloadJson(int attempt) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("method", HttpMethod.POST.name());
        payload.put("url", properties.getBaseUrl() + properties.getConnectPath());
        payload.put("apiCd", properties.getApiCd());
        payload.put("apiKey", maskValue(properties.getApiKey()));
        payload.put("attempt", attempt);
        return vendorCommunicationPayloadGenerator.buildPayloadJson(payload);
    }

    private JsonNode buildResponsePayloadJson(ResponseEntity<FasstoAuthResponse> response, int attempt) {
        Map<String, Object> payload = new LinkedHashMap<>();
        if (FormatValidator.hasValue(response)) {
            payload.put("status", response.getStatusCode().value());
        }

        FasstoAuthResponse body = response != null ? response.getBody() : null;
        if (FormatValidator.hasValue(body) && FormatValidator.hasValue(body.header())) {
            payload.put("code", body.header().code());
            payload.put("message", body.header().msg());
        }

        if (FormatValidator.hasValue(body) && FormatValidator.hasValue(body.data())) {
            payload.put("accessToken", maskValue(body.data().accessToken()));
            payload.put("expreDatetime", body.data().expreDatetime());
        }

        if (FormatValidator.hasValue(body) && FormatValidator.hasValue(body.errorInfo())) {
            payload.put("errorInfo", String.valueOf(body.errorInfo()));
        }

        payload.put("attempt", attempt);
        return vendorCommunicationPayloadGenerator.buildPayloadJson(payload);
    }

    private String maskValue(String value) {
        if (FormatValidator.hasNoValue(value)) {
            return value;
        }

        int visible = Math.min(4, value.length());
        int maskedLength = value.length() - visible;
        if (maskedLength <= 0) {
            return value;
        }

        return "*".repeat(maskedLength) + value.substring(value.length() - visible);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
