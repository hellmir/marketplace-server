package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.marketnote.common.adapter.out.VendorAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.common.utility.http.client.CommunicationFailureHandler;
import com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response.*;
import com.personal.marketnote.fulfillment.configuration.FasstoAuthProperties;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingQuery;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationSenderType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationTargetType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorName;
import com.personal.marketnote.fulfillment.exception.GetFasstoWarehousingFailedException;
import com.personal.marketnote.fulfillment.exception.RegisterFasstoWarehousingFailedException;
import com.personal.marketnote.fulfillment.exception.UpdateFasstoWarehousingFailedException;
import com.personal.marketnote.fulfillment.port.in.result.vendor.*;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoWarehousingPort;
import com.personal.marketnote.fulfillment.port.out.vendor.RegisterFasstoWarehousingPort;
import com.personal.marketnote.fulfillment.port.out.vendor.UpdateFasstoWarehousingPort;
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
import java.util.concurrent.ThreadLocalRandom;

import static com.personal.marketnote.common.utility.ApiConstant.*;

@VendorAdapter
@RequiredArgsConstructor
@Slf4j
public class FasstoWarehousingClient implements RegisterFasstoWarehousingPort, GetFasstoWarehousingPort, UpdateFasstoWarehousingPort {
    private static final String ACCESS_TOKEN_HEADER = "accessToken";
    private static final String CUSTOMER_CODE_PLACEHOLDER = "{customerCode}";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final FasstoAuthProperties properties;
    private final VendorCommunicationRecorder vendorCommunicationRecorder;
    private final VendorCommunicationPayloadGenerator vendorCommunicationPayloadGenerator;
    private final VendorCommunicationFailureHandler vendorCommunicationFailureHandler;

    @Override
    public RegisterFasstoWarehousingResult registerWarehousing(FasstoWarehousingMapper request) {
        RegisterFasstoWarehousingResponse response = executeWarehousingMutation(
                request,
                "REGISTER",
                HttpMethod.POST,
                false
        );
        return mapWarehousingResult(response);
    }

    @Override
    public UpdateFasstoWarehousingResult updateWarehousing(FasstoWarehousingMapper request) {
        RegisterFasstoWarehousingResponse response = executeWarehousingMutation(
                request,
                "UPDATE",
                HttpMethod.PATCH,
                true
        );
        return mapUpdateWarehousingResult(response);
    }

    @Override
    public GetFasstoWarehousingResult getWarehousing(FasstoWarehousingQuery query) {
        if (FormatValidator.hasNoValue(query)) {
            throw new IllegalArgumentException("Fassto warehousing query is required.");
        }

        URI uri = buildWarehousingListUri(query.getCustomerCode(), query.getStartDate(), query.getEndDate());
        HttpEntity<Void> httpEntity = new HttpEntity<>(buildHeaders(query.getAccessToken(), false));

        Exception error = new Exception();
        String failureMessage = null;
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        FulfillmentVendorCommunicationTargetType targetType = FulfillmentVendorCommunicationTargetType.WAREHOUSING;
        FulfillmentVendorName vendorName = FulfillmentVendorName.FASSTO;

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            int attempt = i + 1;
            JsonNode requestPayloadJson = buildListRequestPayloadJson(query, uri, attempt);
            String requestPayload = requestPayloadJson.toString();

            ResponseEntity<String> response;
            try {
                response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        httpEntity,
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

                log.warn("Failed to get Fassto warehousing list: attempt={}, message={}", attempt, e.getMessage(), e);
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }

                sleep(sleepMillis);

                // exponential backoff applied
                sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;

                continue;
            }

            JsonNode responsePayloadJson = buildResponsePayloadJson(response, attempt);
            String responsePayload = responsePayloadJson.toString();

            FasstoWarehousingListResponse parsedResponse = parseWarehousingListResponse(response);
            boolean isSuccess = isWarehousingListSuccess(response, parsedResponse);
            String exception = isSuccess ? null : resolveWarehousingListException(response, parsedResponse);

            vendorCommunicationRecorder.record(
                    targetType,
                    FulfillmentVendorCommunicationType.REQUEST,
                    FulfillmentVendorCommunicationSenderType.SERVER,
                    vendorName,
                    requestPayload,
                    requestPayloadJson,
                    exception
            );
            vendorCommunicationRecorder.record(
                    targetType,
                    FulfillmentVendorCommunicationType.RESPONSE,
                    FulfillmentVendorCommunicationSenderType.VENDOR,
                    vendorName,
                    responsePayload,
                    responsePayloadJson,
                    exception
            );

            if (isSuccess) {
                return mapWarehousingListResult(parsedResponse);
            }

            String vendorMessage = resolveVendorMessage(parsedResponse, FormatValidator.hasValue(response) ? response.getBody() : null);
            if (FormatValidator.hasValue(vendorMessage)) {
                failureMessage = vendorMessage;
                error = new Exception(vendorMessage);
            }

            log.warn("Fassto warehousing list request failed: attempt={}, status={}, exception={}",
                    attempt,
                    FormatValidator.hasValue(response) ? response.getStatusCode() : null,
                    exception
            );

            if (CommunicationFailureHandler.isCertainFailure(response)) {
                break;
            }

            sleep(sleepMillis);

            // exponential backoff applied
            sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
        }

        log.error("Failed to get Fassto warehousing list: {} with error: {}", uri, error.getMessage(), error);
        throw new GetFasstoWarehousingFailedException(failureMessage, new IOException(error));
    }

    private RegisterFasstoWarehousingResponse executeWarehousingMutation(
            FasstoWarehousingMapper request,
            String action,
            HttpMethod method,
            boolean isUpdate
    ) {
        if (FormatValidator.hasNoValue(request)) {
            throw new IllegalArgumentException("Fassto warehousing request is required.");
        }

        URI uri = buildWarehousingUri(request.getCustomerCode());
        HttpEntity<List<Map<String, Object>>> httpEntity = new HttpEntity<>(
                request.toPayload(),
                buildHeaders(request.getAccessToken())
        );

        Exception error = new Exception();
        String failureMessage = null;
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        FulfillmentVendorCommunicationTargetType targetType = FulfillmentVendorCommunicationTargetType.WAREHOUSING;
        FulfillmentVendorName vendorName = FulfillmentVendorName.FASSTO;

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            int attempt = i + 1;
            JsonNode requestPayloadJson = buildRequestPayloadJson(request, uri, attempt, action, method);
            String requestPayload = requestPayloadJson.toString();

            ResponseEntity<String> response;
            try {
                response = restTemplate.exchange(
                        uri,
                        method,
                        httpEntity,
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

                log.warn("Failed to {} Fassto warehousing request: attempt={}, message={}",
                        action.toLowerCase(),
                        attempt,
                        e.getMessage(),
                        e
                );
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }

                sleep(sleepMillis);
                // exponential backoff 적용
                sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
                continue;
            }

            JsonNode responsePayloadJson = buildResponsePayloadJson(response, attempt);
            String responsePayload = responsePayloadJson.toString();

            RegisterFasstoWarehousingResponse parsedResponse = parseResponseBody(response);
            boolean isSuccess = isSuccessResponse(response, parsedResponse);
            String exception = isSuccess
                    ? null
                    : resolveResponseException(response, parsedResponse);

            vendorCommunicationRecorder.record(
                    targetType,
                    FulfillmentVendorCommunicationType.REQUEST,
                    FulfillmentVendorCommunicationSenderType.SERVER,
                    vendorName,
                    requestPayload,
                    requestPayloadJson,
                    exception
            );
            vendorCommunicationRecorder.record(
                    targetType,
                    FulfillmentVendorCommunicationType.RESPONSE,
                    FulfillmentVendorCommunicationSenderType.VENDOR,
                    vendorName,
                    responsePayload,
                    responsePayloadJson,
                    exception
            );

            if (isSuccess) {
                return parsedResponse;
            }

            String vendorMessage = resolveVendorMessage(parsedResponse, FormatValidator.hasValue(response) ? response.getBody() : null);
            if (FormatValidator.hasValue(vendorMessage)) {
                failureMessage = vendorMessage;
                error = new Exception(vendorMessage);
            }

            log.warn("Fassto warehousing {} failed: attempt={}, status={}, exception={}",
                    action.toLowerCase(),
                    attempt,
                    FormatValidator.hasValue(response) ? response.getStatusCode() : null,
                    exception
            );

            if (CommunicationFailureHandler.isCertainFailure(response)) {
                break;
            }

            sleep(sleepMillis);
            // exponential backoff 적용
            sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
        }

        log.error("Failed to {} Fassto warehousing request: {} with error: {}",
                action.toLowerCase(),
                uri,
                error.getMessage(),
                error
        );

        if (isUpdate) {
            throw new UpdateFasstoWarehousingFailedException(failureMessage, new IOException(error));
        }
        throw new RegisterFasstoWarehousingFailedException(failureMessage, new IOException(error));
    }

    private URI buildWarehousingUri(String customerCode) {
        validateWarehousingProperties();
        return UriComponentsBuilder.fromUriString(properties.getBaseUrl())
                .path(properties.getWarehousingPath())
                .buildAndExpand(customerCode)
                .toUri();
    }

    private URI buildWarehousingListUri(String customerCode, String startDate, String endDate) {
        validateWarehousingListProperties();
        return UriComponentsBuilder.fromUriString(properties.getBaseUrl())
                .path(properties.getWarehousingListPath())
                .buildAndExpand(customerCode, startDate, endDate)
                .toUri();
    }

    private HttpHeaders buildHeaders(String accessToken) {
        return buildHeaders(accessToken, true);
    }

    private HttpHeaders buildHeaders(String accessToken, boolean includeContentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (includeContentType) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        headers.add(ACCESS_TOKEN_HEADER, accessToken);
        return headers;
    }

    private void validateWarehousingProperties() {
        if (FormatValidator.hasNoValue(properties.getBaseUrl())) {
            throw new IllegalStateException("Fassto base URL is required.");
        }
        if (FormatValidator.hasNoValue(properties.getWarehousingPath())) {
            throw new IllegalStateException("Fassto warehousing path is required.");
        }
        if (!properties.getWarehousingPath().contains(CUSTOMER_CODE_PLACEHOLDER)) {
            throw new IllegalStateException("Fassto warehousing path must include {customerCode}.");
        }
    }

    private void validateWarehousingListProperties() {
        if (FormatValidator.hasNoValue(properties.getBaseUrl())) {
            throw new IllegalStateException("Fassto base URL is required.");
        }
        if (FormatValidator.hasNoValue(properties.getWarehousingListPath())) {
            throw new IllegalStateException("Fassto warehousing list path is required.");
        }
        if (!properties.getWarehousingListPath().contains(CUSTOMER_CODE_PLACEHOLDER)) {
            throw new IllegalStateException("Fassto warehousing list path must include {customerCode}.");
        }
        if (!properties.getWarehousingListPath().contains("{startDate}")) {
            throw new IllegalStateException("Fassto warehousing list path must include {startDate}.");
        }
        if (!properties.getWarehousingListPath().contains("{endDate}")) {
            throw new IllegalStateException("Fassto warehousing list path must include {endDate}.");
        }
    }

    private RegisterFasstoWarehousingResponse parseResponseBody(ResponseEntity<String> response) {
        if (FormatValidator.hasNoValue(response)) {
            return null;
        }

        String body = response.getBody();
        if (FormatValidator.hasNoValue(body)) {
            return null;
        }

        try {
            return objectMapper.readValue(body, RegisterFasstoWarehousingResponse.class);
        } catch (Exception e) {
            log.warn("Failed to parse Fassto warehousing response: {}", e.getMessage(), e);
            return null;
        }
    }

    private FasstoWarehousingListResponse parseWarehousingListResponse(ResponseEntity<String> response) {
        if (FormatValidator.hasNoValue(response)) {
            return null;
        }

        String body = response.getBody();
        if (FormatValidator.hasNoValue(body)) {
            return null;
        }

        try {
            return objectMapper.readValue(body, FasstoWarehousingListResponse.class);
        } catch (Exception e) {
            log.warn("Failed to parse Fassto warehousing list response: {}", e.getMessage(), e);
            return null;
        }
    }

    private boolean isSuccessResponse(ResponseEntity<String> response, RegisterFasstoWarehousingResponse parsedResponse) {
        return FormatValidator.hasValue(response)
                && response.getStatusCode().value() == 200
                && FormatValidator.hasValue(parsedResponse)
                && parsedResponse.isSuccess()
                && FormatValidator.hasValue(parsedResponse.data());
    }

    private boolean isWarehousingListSuccess(ResponseEntity<String> response, FasstoWarehousingListResponse parsedResponse) {
        return FormatValidator.hasValue(response)
                && response.getStatusCode().value() == 200
                && FormatValidator.hasValue(parsedResponse)
                && parsedResponse.isSuccess();
    }

    private String resolveResponseException(ResponseEntity<String> response, RegisterFasstoWarehousingResponse parsedResponse) {
        if (FormatValidator.hasNoValue(response)) {
            return "NO_RESPONSE";
        }
        if (response.getStatusCode().value() != 200) {
            return "HTTP_" + response.getStatusCode().value();
        }
        if (FormatValidator.hasNoValue(parsedResponse)) {
            return "INVALID_RESPONSE";
        }
        if (FormatValidator.hasNoValue(parsedResponse.header()) || !parsedResponse.header().isSuccess()) {
            return "HEADER_FAILURE";
        }
        if (FormatValidator.hasNoValue(parsedResponse.data())) {
            return "DATA_MISSING";
        }
        return "UNKNOWN_FAILURE";
    }

    private String resolveWarehousingListException(
            ResponseEntity<String> response,
            FasstoWarehousingListResponse parsedResponse
    ) {
        if (FormatValidator.hasNoValue(response)) {
            return "NO_RESPONSE";
        }
        if (response.getStatusCode().value() != 200) {
            return "HTTP_" + response.getStatusCode().value();
        }
        if (FormatValidator.hasNoValue(parsedResponse)) {
            return "INVALID_RESPONSE";
        }
        if (FormatValidator.hasNoValue(parsedResponse.header()) || !parsedResponse.header().isSuccess()) {
            return "HEADER_FAILURE";
        }
        if (FormatValidator.hasNoValue(parsedResponse.data())) {
            return "DATA_MISSING";
        }
        return "UNKNOWN_FAILURE";
    }

    private JsonNode buildListRequestPayloadJson(FasstoWarehousingQuery query, URI uri, int attempt) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("method", HttpMethod.GET.name());
        payload.put("url", uri.toString());
        payload.put("customerCode", query.getCustomerCode());
        payload.put("startDate", query.getStartDate());
        payload.put("endDate", query.getEndDate());
        payload.put(ACCESS_TOKEN_HEADER, maskValue(query.getAccessToken()));
        payload.put("attempt", attempt);
        return vendorCommunicationPayloadGenerator.buildPayloadJson(payload);
    }

    private JsonNode buildRequestPayloadJson(
            FasstoWarehousingMapper request,
            URI uri,
            int attempt,
            String action,
            HttpMethod method
    ) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("method", method.name());
        payload.put("url", uri.toString());
        payload.put("customerCode", request.getCustomerCode());
        payload.put(ACCESS_TOKEN_HEADER, maskValue(request.getAccessToken()));
        payload.put("body", request.toPayload());
        payload.put("action", action);
        payload.put("attempt", attempt);
        return vendorCommunicationPayloadGenerator.buildPayloadJson(payload);
    }

    private JsonNode buildResponsePayloadJson(ResponseEntity<String> response, int attempt) {
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

    private RegisterFasstoWarehousingResult mapWarehousingResult(RegisterFasstoWarehousingResponse response) {
        List<RegisterFasstoWarehousingItemResult> warehousing = response.data().stream()
                .map(this::mapWarehousingItem)
                .toList();
        Integer dataCount = FormatValidator.hasValue(response.header()) ? response.header().dataCount() : null;
        return RegisterFasstoWarehousingResult.of(dataCount, warehousing);
    }

    private UpdateFasstoWarehousingResult mapUpdateWarehousingResult(RegisterFasstoWarehousingResponse response) {
        List<UpdateFasstoWarehousingItemResult> warehousing = response.data().stream()
                .map(this::mapUpdateWarehousingItem)
                .toList();
        Integer dataCount = FormatValidator.hasValue(response.header()) ? response.header().dataCount() : null;
        return UpdateFasstoWarehousingResult.of(dataCount, warehousing);
    }

    private GetFasstoWarehousingResult mapWarehousingListResult(FasstoWarehousingListResponse response) {
        List<FasstoWarehousingInfoResult> warehousing = response.data().stream()
                .map(this::mapWarehousingInfo)
                .toList();
        Integer dataCount = FormatValidator.hasValue(response.header()) ? response.header().dataCount() : null;
        return GetFasstoWarehousingResult.of(dataCount, warehousing);
    }

    private RegisterFasstoWarehousingItemResult mapWarehousingItem(RegisterFasstoWarehousingItemResponse item) {
        return RegisterFasstoWarehousingItemResult.of(
                item.msg(),
                item.code(),
                item.slipNo(),
                item.ordNo()
        );
    }

    private UpdateFasstoWarehousingItemResult mapUpdateWarehousingItem(RegisterFasstoWarehousingItemResponse item) {
        return UpdateFasstoWarehousingItemResult.of(
                item.msg(),
                item.code(),
                item.slipNo(),
                item.ordNo()
        );
    }

    private FasstoWarehousingInfoResult mapWarehousingInfo(FasstoWarehousingItemResponse item) {
        List<Object> goodsSerialNo = FormatValidator.hasValue(item.goodsSerialNo())
                ? item.goodsSerialNo()
                : List.of();

        return FasstoWarehousingInfoResult.of(
                item.ordDt(),
                item.whCd(),
                item.whNm(),
                item.ordNo(),
                item.slipNo(),
                item.cstCd(),
                item.cstNm(),
                item.supCd(),
                item.cstSupCd(),
                item.sku(),
                item.supNm(),
                item.ordQty(),
                item.inQty(),
                item.inWay(),
                item.inWayNm(),
                item.parcelComp(),
                item.parcelInvoiceNo(),
                item.wrkStat(),
                item.wrkStatNm(),
                item.emgrYn(),
                item.remark(),
                goodsSerialNo
        );
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
            // 대상 서비스 장애 시 요청 트래픽 폭주를 방지하기 위해 jitter 설정
            long jitteredSleepMillis = ThreadLocalRandom.current()
                    .nextLong(Math.max(1L, millis) + 1);
            Thread.sleep(jitteredSleepMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String resolveVendorMessage(RegisterFasstoWarehousingResponse response, String rawBody) {
        if (FormatValidator.hasValue(response)) {
            String message = response.resolveErrorMessage();
            if (FormatValidator.hasValue(message)) {
                return message;
            }
        }
        return resolveVendorMessage(rawBody);
    }

    private String resolveVendorMessage(FasstoWarehousingListResponse response, String rawBody) {
        if (FormatValidator.hasValue(response)) {
            String message = response.resolveErrorMessage();
            if (FormatValidator.hasValue(message)) {
                return message;
            }
        }
        return resolveVendorMessage(rawBody);
    }

    private String resolveVendorMessage(String rawBody) {
        if (FormatValidator.hasNoValue(rawBody)) {
            return null;
        }

        try {
            FasstoErrorResponse response = objectMapper.readValue(rawBody, FasstoErrorResponse.class);
            return response.resolveErrorMessage();
        } catch (Exception e) {
            log.warn("Failed to parse Fassto error response: {}", e.getMessage(), e);
            return null;
        }
    }

    private String resolveVendorMessageFromException(Exception e) {
        if (!(e instanceof RestClientResponseException responseException)) {
            return null;
        }

        String body = responseException.getResponseBodyAsString();

        return resolveVendorMessage(body);
    }
}
