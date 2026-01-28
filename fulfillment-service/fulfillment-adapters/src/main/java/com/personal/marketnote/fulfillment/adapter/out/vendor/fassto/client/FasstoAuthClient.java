package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.marketnote.common.adapter.out.VendorAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response.FasstoAuthResponse;
import com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response.FasstoErrorResponse;
import com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response.FasstoResponseHeader;
import com.personal.marketnote.fulfillment.configuration.FasstoAuthProperties;
import com.personal.marketnote.fulfillment.domain.FasstoAccessToken;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationSenderType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationTargetType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorName;
import com.personal.marketnote.fulfillment.exception.FasstoAuthDisconnectFailedException;
import com.personal.marketnote.fulfillment.exception.FasstoAuthRequestFailedException;
import com.personal.marketnote.fulfillment.port.out.vendor.DisconnectFasstoAuthPort;
import com.personal.marketnote.fulfillment.port.out.vendor.RequestFasstoAuthPort;
import com.personal.marketnote.fulfillment.utility.VendorCommunicationFailureHandler;
import com.personal.marketnote.fulfillment.utility.VendorCommunicationPayloadGenerator;
import com.personal.marketnote.fulfillment.utility.VendorCommunicationRecorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
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
public class FasstoAuthClient implements RequestFasstoAuthPort, DisconnectFasstoAuthPort {
    private static final String API_CD_PARAM = "apiCd";
    private static final String API_KEY_PARAM = "apiKey";
    private static final String ACCESS_TOKEN_HEADER = "accessToken";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final FasstoAuthProperties properties;
    private final VendorCommunicationRecorder vendorCommunicationRecorder;
    private final VendorCommunicationPayloadGenerator vendorCommunicationPayloadGenerator;
    private final VendorCommunicationFailureHandler vendorCommunicationFailureHandler;

    @Override
    public FasstoAccessToken requestAccessToken() {
        URI uri = buildAuthUri();
        HttpEntity<Void> request = new HttpEntity<>(buildHeaders());

        Exception error = new Exception();
        String failureMessage = null;
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        FulfillmentVendorCommunicationTargetType targetType = FulfillmentVendorCommunicationTargetType.AUTHENTICATION;
        FulfillmentVendorName vendorName = FulfillmentVendorName.FASSTO;

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            int attempt = i + 1;
            JsonNode requestPayloadJson = buildRequestPayloadJson(attempt);
            String requestPayload = requestPayloadJson.toString();

            ResponseEntity<FasstoAuthResponse> response;
            try {
                response = restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        request,
                        FasstoAuthResponse.class
                );
            } catch (Exception e) {
                Map<String, Object> errorPayload = new LinkedHashMap<>();
                errorPayload.put("error", e.getClass().getSimpleName());
                errorPayload.put("message", e.getMessage());
                errorPayload.put("attempt", attempt);

                vendorCommunicationFailureHandler.handleFailure(
                        targetType,
                        vendorName,
                        requestPayload,
                        requestPayloadJson,
                        errorPayload,
                        e
                );

                String vendorMessage = resolveVendorMessageFromException(e);
                if (FormatValidator.hasValue(vendorMessage)) {
                    failureMessage = vendorMessage;
                    error = new Exception(vendorMessage);
                }

                log.warn("Failed to request Fassto auth: attempt={}, message={}", attempt, e.getMessage(), e);
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }

                sleep(sleepMillis);
                sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
                continue;
            }

            JsonNode responsePayloadJson = buildResponsePayloadJson(response, attempt);
            String responsePayload = responsePayloadJson.toString();
            String exception = resolveAuthException(response);

            recordCommunication(
                    targetType,
                    vendorName,
                    FulfillmentVendorCommunicationType.REQUEST,
                    requestPayload,
                    requestPayloadJson,
                    exception
            );
            recordCommunication(
                    targetType,
                    vendorName,
                    FulfillmentVendorCommunicationType.RESPONSE,
                    responsePayload,
                    responsePayloadJson,
                    exception
            );

            FasstoAccessToken accessToken = parseResponse(response);

            if (FormatValidator.hasValue(accessToken)) {
                return accessToken;
            }

            String vendorMessage = resolveAuthFailureMessage(response);
            if (FormatValidator.hasValue(vendorMessage)) {
                failureMessage = vendorMessage;
                error = new Exception(vendorMessage);
            }

            sleep(sleepMillis);
            sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
        }

        log.error("Failed to request Fassto auth: {} with error: {}", uri, error.getMessage(), error);
        throw new FasstoAuthRequestFailedException(failureMessage, new IOException(error));
    }

    @Override
    public void disconnectAccessToken(String accessToken) {
        if (FormatValidator.hasNoValue(accessToken)) {
            throw new IllegalArgumentException("Fassto access token is required.");
        }

        URI uri = buildDisconnectUri();
        HttpHeaders headers = buildHeaders();
        headers.add(ACCESS_TOKEN_HEADER, accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        Exception error = new Exception();
        String failureMessage = null;
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        FulfillmentVendorCommunicationTargetType targetType = FulfillmentVendorCommunicationTargetType.AUTHENTICATION;
        FulfillmentVendorName vendorName = FulfillmentVendorName.FASSTO;

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            int attempt = i + 1;
            JsonNode requestPayloadJson = buildDisconnectRequestPayloadJson(accessToken, attempt);
            String requestPayload = requestPayloadJson.toString();

            ResponseEntity<String> response;
            try {
                response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        request,
                        String.class
                );
            } catch (Exception e) {
                Map<String, Object> errorPayload = new LinkedHashMap<>();
                errorPayload.put("error", e.getClass().getSimpleName());
                errorPayload.put("message", e.getMessage());
                errorPayload.put("attempt", attempt);

                vendorCommunicationFailureHandler.handleFailure(
                        targetType,
                        vendorName,
                        requestPayload,
                        requestPayloadJson,
                        errorPayload,
                        e
                );

                String vendorMessage = resolveVendorMessageFromException(e);
                if (FormatValidator.hasValue(vendorMessage)) {
                    failureMessage = vendorMessage;
                    error = new Exception(vendorMessage);
                }

                log.warn("Failed to disconnect Fassto auth: attempt={}, message={}", attempt, e.getMessage(), e);
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }

                sleep(sleepMillis);
                sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
                continue;
            }

            JsonNode responsePayloadJson = buildDisconnectResponsePayloadJson(response, attempt);
            String responsePayload = responsePayloadJson.toString();

            boolean isSuccess = response.getStatusCode().value() == 200;
            String exception = isSuccess ? null : "HTTP_" + response.getStatusCode().value();

            recordCommunication(
                    targetType,
                    vendorName,
                    FulfillmentVendorCommunicationType.REQUEST,
                    requestPayload,
                    requestPayloadJson,
                    exception
            );
            recordCommunication(
                    targetType,
                    vendorName,
                    FulfillmentVendorCommunicationType.RESPONSE,
                    responsePayload,
                    responsePayloadJson,
                    exception
            );

            if (isSuccess) {
                return;
            }

            String vendorMessage = resolveDisconnectFailureMessage(response);
            if (FormatValidator.hasValue(vendorMessage)) {
                failureMessage = vendorMessage;
                error = new Exception(vendorMessage);
            }

            log.warn("Fassto disconnect returned non-200 status: attempt={}, status={}",
                    attempt,
                    response.getStatusCode()
            );

            sleep(sleepMillis);
            sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
        }

        log.error("Failed to disconnect Fassto auth: {} with error: {}", uri, error.getMessage(), error);
        throw new FasstoAuthDisconnectFailedException(failureMessage, new IOException(error));
    }

    private URI buildAuthUri() {
        validateAuthProperties();
        return UriComponentsBuilder.fromUriString(properties.getBaseUrl())
                .path(properties.getConnectPath())
                .queryParam(API_CD_PARAM, properties.getApiCd())
                .queryParam(API_KEY_PARAM, properties.getApiKey())
                .build(true)
                .toUri();
    }

    private URI buildDisconnectUri() {
        validateDisconnectProperties();
        return UriComponentsBuilder.fromUriString(properties.getBaseUrl())
                .path(properties.getDisconnectPath())
                .build(true)
                .toUri();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private void validateAuthProperties() {
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

    private void validateDisconnectProperties() {
        if (FormatValidator.hasNoValue(properties.getBaseUrl())) {
            throw new IllegalStateException("Fassto base URL is required.");
        }
        if (FormatValidator.hasNoValue(properties.getDisconnectPath())) {
            throw new IllegalStateException("Fassto auth disconnect path is required.");
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

        FasstoResponseHeader header = response.header();
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

        FasstoAuthResponse body = FormatValidator.hasValue(response) ? response.getBody() : null;
        if (FormatValidator.hasValue(body) && FormatValidator.hasValue(body.header())) {
            payload.put("code", body.header().code());
            payload.put("message", body.header().msg());
        }

        if (FormatValidator.hasValue(body) && FormatValidator.hasValue(body.data())) {
            payload.put("accessToken", maskValue(body.data().accessToken()));
            payload.put("expreDatetime", body.data().expreDatetime());
        }

        if (FormatValidator.hasValue(body) && FormatValidator.hasValue(body.errorInfo())) {
            payload.put("errorInfo", body.errorInfo());
        }

        payload.put("attempt", attempt);
        return vendorCommunicationPayloadGenerator.buildPayloadJson(payload);
    }

    private JsonNode buildDisconnectRequestPayloadJson(String accessToken, int attempt) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("method", HttpMethod.GET.name());
        payload.put("url", properties.getBaseUrl() + properties.getDisconnectPath());
        payload.put(ACCESS_TOKEN_HEADER, maskValue(accessToken));
        payload.put("attempt", attempt);
        return vendorCommunicationPayloadGenerator.buildPayloadJson(payload);
    }

    private JsonNode buildDisconnectResponsePayloadJson(ResponseEntity<String> response, int attempt) {
        Map<String, Object> payload = new LinkedHashMap<>();
        if (FormatValidator.hasValue(response)) {
            payload.put("status", response.getStatusCode().value());
            payload.put("headers", toSingleValueMap(response.getHeaders()));

            String body = response.getBody();
            if (FormatValidator.hasValue(body)) {
                payload.put("body", body);
            }
        }
        payload.put("attempt", attempt);
        return vendorCommunicationPayloadGenerator.buildPayloadJson(payload);
    }

    private Map<String, String> toSingleValueMap(HttpHeaders headers) {
        if (FormatValidator.hasNoValue(headers)) {
            return Map.of();
        }

        return headers.toSingleValueMap();
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

    private void recordCommunication(
            FulfillmentVendorCommunicationTargetType targetType,
            FulfillmentVendorName vendorName,
            FulfillmentVendorCommunicationType communicationType,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        FulfillmentVendorCommunicationSenderType sender = communicationType == FulfillmentVendorCommunicationType.REQUEST
                ? FulfillmentVendorCommunicationSenderType.SERVER
                : FulfillmentVendorCommunicationSenderType.VENDOR;
        if (FormatValidator.hasValue(exception)) {
            vendorCommunicationRecorder.record(
                    targetType,
                    communicationType,
                    sender,
                    vendorName,
                    payload,
                    payloadJson,
                    exception
            );
            return;
        }

        vendorCommunicationRecorder.record(
                targetType,
                communicationType,
                sender,
                vendorName,
                payload,
                payloadJson
        );
    }

    private String resolveAuthException(ResponseEntity<FasstoAuthResponse> response) {
        if (FormatValidator.hasNoValue(response)) {
            return "NO_RESPONSE";
        }
        if (!response.getStatusCode().is2xxSuccessful()) {
            return "HTTP_" + response.getStatusCode().value();
        }

        FasstoAuthResponse body = response.getBody();
        if (FormatValidator.hasNoValue(body)) {
            return "EMPTY_BODY";
        }
        if (!body.isSuccess()) {
            return "HEADER_FAILURE";
        }
        if (FormatValidator.hasNoValue(body.data())
                || FormatValidator.hasNoValue(body.data().accessToken())
                || FormatValidator.hasNoValue(body.data().expreDatetime())
        ) {
            return "MISSING_TOKEN";
        }

        return null;
    }

    private String resolveAuthFailureMessage(ResponseEntity<FasstoAuthResponse> response) {
        if (FormatValidator.hasNoValue(response)) {
            return null;
        }

        FasstoAuthResponse body = response.getBody();
        if (FormatValidator.hasNoValue(body)) {
            return null;
        }

        return body.resolveErrorMessage();
    }

    private String resolveDisconnectFailureMessage(ResponseEntity<String> response) {
        if (FormatValidator.hasNoValue(response)) {
            return null;
        }

        String body = response.getBody();
        if (FormatValidator.hasNoValue(body)) {
            return null;
        }

        try {
            FasstoErrorResponse parsedResponse = objectMapper.readValue(body, FasstoErrorResponse.class);
            return parsedResponse.resolveErrorMessage();
        } catch (Exception e) {
            log.warn("Failed to parse Fassto disconnect error response: {}", e.getMessage(), e);
            return null;
        }
    }

    private String resolveVendorMessageFromException(Exception e) {
        if (!(e instanceof RestClientResponseException responseException)) {
            return null;
        }

        String body = responseException.getResponseBodyAsString();
        if (FormatValidator.hasNoValue(body)) {
            return null;
        }

        try {
            FasstoErrorResponse parsedResponse = objectMapper.readValue(body, FasstoErrorResponse.class);
            return parsedResponse.resolveErrorMessage();
        } catch (Exception parseException) {
            log.warn("Failed to parse Fassto error response from exception: {}", parseException.getMessage(), parseException);
            return null;
        }
    }
}
