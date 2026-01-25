package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.marketnote.common.adapter.out.VendorAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response.FasstoWarehousesItemResponse;
import com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response.FasstoWarehousesResponse;
import com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response.RegisterFasstoWarehouseResponse;
import com.personal.marketnote.fulfillment.configuration.FasstoAuthProperties;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehouse.FasstoWarehouseMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehouse.FasstoWarehouseQuery;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationTargetType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorName;
import com.personal.marketnote.fulfillment.exception.GetFasstoWarehousesFailedException;
import com.personal.marketnote.fulfillment.exception.RegisterFasstoWarehouseFailedException;
import com.personal.marketnote.fulfillment.port.in.result.vendor.FasstoWarehouseInfoResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousesResult;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehouseResult;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoWarehousesPort;
import com.personal.marketnote.fulfillment.port.out.vendor.RegisterFasstoWarehousePort;
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
public class FasstoWarehouseClient implements RegisterFasstoWarehousePort, GetFasstoWarehousesPort {
    private static final String ACCESS_TOKEN_HEADER = "accessToken";
    private static final String CUSTOMER_CODE_PLACEHOLDER = "{customerCode}";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final FasstoAuthProperties properties;
    private final VendorCommunicationRecorder vendorCommunicationRecorder;
    private final VendorCommunicationPayloadGenerator vendorCommunicationPayloadGenerator;
    private final VendorCommunicationFailureHandler vendorCommunicationFailureHandler;

    @Override
    public RegisterFasstoWarehouseResult registerWarehouse(FasstoWarehouseMapper request) {
        if (FormatValidator.hasNoValue(request)) {
            throw new IllegalArgumentException("Fassto shop request is required.");
        }

        URI uri = buildShopUri(request.getCustomerCode());
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(request.toPayload(), buildHeaders(request.getAccessToken()));

        Exception error = new Exception();
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        FulfillmentVendorCommunicationTargetType targetType = FulfillmentVendorCommunicationTargetType.WAREHOUSE;
        FulfillmentVendorName vendorName = FulfillmentVendorName.FASSTO;

        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            int attempt = i + 1;
            JsonNode requestPayloadJson = buildRequestPayloadJson(request, uri, attempt);
            String requestPayload = requestPayloadJson.toString();

            ResponseEntity<String> response;
            try {
                response = restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
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

                log.warn("Failed to register Fassto shop: attempt={}, message={}", attempt, e.getMessage(), e);
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }

                sleep(sleepMillis);
                sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
                continue;
            }

            JsonNode responsePayloadJson = buildResponsePayloadJson(response, attempt);
            String responsePayload = responsePayloadJson.toString();

            RegisterFasstoWarehouseResponse parsedResponse = parseResponseBody(response);
            boolean isSuccess = isSuccessResponse(response, parsedResponse);
            String exception = isSuccess ? null : resolveResponseException(response, parsedResponse);

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
                return RegisterFasstoWarehouseResult.of(
                        parsedResponse.data().msg(),
                        parsedResponse.data().code(),
                        parsedResponse.data().shopCd()
                );
            }

            log.warn("Fassto shop registration failed: attempt={}, status={}, exception={}",
                    attempt,
                    response != null ? response.getStatusCode() : null,
                    exception
            );

            sleep(sleepMillis);
            sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
        }

        log.error("Failed to register Fassto shop: {} with error: {}", uri, error.getMessage(), error);
        throw new RegisterFasstoWarehouseFailedException(new IOException(error));
    }

    @Override
    public GetFasstoWarehousesResult getWarehouses(FasstoWarehouseQuery query) {
        if (FormatValidator.hasNoValue(query)) {
            throw new IllegalArgumentException("Fassto warehouse query is required.");
        }

        URI uri = buildShopUri(query.getCustomerCode());
        HttpEntity<Void> httpEntity = new HttpEntity<>(buildHeaders(query.getAccessToken(), false));

        Exception error = new Exception();
        long sleepMillis = INTER_SERVER_DEFAULT_RETRIAL_PENDING_MILLI_SECOND;
        FulfillmentVendorCommunicationTargetType targetType = FulfillmentVendorCommunicationTargetType.WAREHOUSE;
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

                log.warn("Failed to get Fassto warehouse list: attempt={}, message={}", attempt, e.getMessage(), e);
                if (i == INTER_SERVER_MAX_REQUEST_COUNT - 1) {
                    error = e;
                }

                sleep(sleepMillis);
                sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
                continue;
            }

            JsonNode responsePayloadJson = buildResponsePayloadJson(response, attempt);
            String responsePayload = responsePayloadJson.toString();

            FasstoWarehousesResponse parsedResponse = parseWarehousesResponse(response);
            boolean isSuccess = isWarehousesSuccess(response, parsedResponse);
            String exception = isSuccess ? null : resolveWarehousesException(response, parsedResponse);

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
                return mapWarehousesResult(parsedResponse);
            }

            log.warn("Fassto warehouse list request failed: attempt={}, status={}, exception={}",
                    attempt,
                    response != null ? response.getStatusCode() : null,
                    exception
            );

            sleep(sleepMillis);
            sleepMillis = sleepMillis * INTER_SERVER_DEFAULT_EXPONENTIAL_BACKOFF_VALUE;
        }

        log.error("Failed to get Fassto warehouse list: {} with error: {}", uri, error.getMessage(), error);
        throw new GetFasstoWarehousesFailedException(new IOException(error));
    }

    private URI buildShopUri(String customerCode) {
        validateShopProperties();
        return UriComponentsBuilder.fromUriString(properties.getBaseUrl())
                .path(properties.getShopPath())
                .buildAndExpand(customerCode)
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

    private void validateShopProperties() {
        if (FormatValidator.hasNoValue(properties.getBaseUrl())) {
            throw new IllegalStateException("Fassto base URL is required.");
        }
        if (FormatValidator.hasNoValue(properties.getShopPath())) {
            throw new IllegalStateException("Fassto shop path is required.");
        }
        if (!properties.getShopPath().contains(CUSTOMER_CODE_PLACEHOLDER)) {
            throw new IllegalStateException("Fassto shop path must include {customerCode}.");
        }
    }

    private RegisterFasstoWarehouseResponse parseResponseBody(ResponseEntity<String> response) {
        if (FormatValidator.hasNoValue(response)) {
            return null;
        }

        String body = response.getBody();
        if (FormatValidator.hasNoValue(body)) {
            return null;
        }

        try {
            return objectMapper.readValue(body, RegisterFasstoWarehouseResponse.class);
        } catch (Exception e) {
            log.warn("Failed to parse Fassto shop response: {}", e.getMessage(), e);
            return null;
        }
    }

    private boolean isSuccessResponse(ResponseEntity<String> response, RegisterFasstoWarehouseResponse parsedResponse) {
        return response != null
                && response.getStatusCode().value() == 200
                && parsedResponse != null
                && parsedResponse.isSuccess()
                && parsedResponse.data() != null;
    }

    private String resolveResponseException(ResponseEntity<String> response, RegisterFasstoWarehouseResponse parsedResponse) {
        if (response == null) {
            return "NO_RESPONSE";
        }
        if (response.getStatusCode().value() != 200) {
            return "HTTP_" + response.getStatusCode().value();
        }
        if (parsedResponse == null) {
            return "INVALID_RESPONSE";
        }
        if (parsedResponse.header() == null || !parsedResponse.header().isSuccess()) {
            return "HEADER_FAILURE";
        }
        if (parsedResponse.data() == null || !parsedResponse.data().isSuccess()) {
            return "DATA_FAILURE";
        }
        return "UNKNOWN_FAILURE";
    }

    private JsonNode buildRequestPayloadJson(FasstoWarehouseMapper request, URI uri, int attempt) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("method", HttpMethod.POST.name());
        payload.put("url", uri.toString());
        payload.put("customerCode", request.getCustomerCode());
        payload.put(ACCESS_TOKEN_HEADER, maskValue(request.getAccessToken()));
        payload.put("body", request.toPayload());
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

    private void recordCommunication(
            FulfillmentVendorCommunicationTargetType targetType,
            FulfillmentVendorName vendorName,
            FulfillmentVendorCommunicationType communicationType,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        if (FormatValidator.hasValue(exception)) {
            vendorCommunicationRecorder.record(
                    targetType,
                    communicationType,
                    null,
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
                null,
                vendorName,
                payload,
                payloadJson
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
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private JsonNode buildListRequestPayloadJson(FasstoWarehouseQuery query, URI uri, int attempt) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("method", HttpMethod.GET.name());
        payload.put("url", uri.toString());
        payload.put("customerCode", query.getCustomerCode());
        payload.put(ACCESS_TOKEN_HEADER, maskValue(query.getAccessToken()));
        payload.put("attempt", attempt);
        return vendorCommunicationPayloadGenerator.buildPayloadJson(payload);
    }

    private FasstoWarehousesResponse parseWarehousesResponse(ResponseEntity<String> response) {
        if (FormatValidator.hasNoValue(response)) {
            return null;
        }

        String body = response.getBody();
        if (FormatValidator.hasNoValue(body)) {
            return null;
        }

        try {
            return objectMapper.readValue(body, FasstoWarehousesResponse.class);
        } catch (Exception e) {
            log.warn("Failed to parse Fassto warehouse list response: {}", e.getMessage(), e);
            return null;
        }
    }

    private boolean isWarehousesSuccess(ResponseEntity<String> response, FasstoWarehousesResponse parsedResponse) {
        return response != null
                && response.getStatusCode().value() == 200
                && parsedResponse != null
                && parsedResponse.isSuccess();
    }

    private String resolveWarehousesException(
            ResponseEntity<String> response,
            FasstoWarehousesResponse parsedResponse
    ) {
        if (response == null) {
            return "NO_RESPONSE";
        }
        if (response.getStatusCode().value() != 200) {
            return "HTTP_" + response.getStatusCode().value();
        }
        if (parsedResponse == null) {
            return "INVALID_RESPONSE";
        }
        if (parsedResponse.header() == null || !parsedResponse.header().isSuccess()) {
            return "HEADER_FAILURE";
        }
        if (parsedResponse.data() == null) {
            return "DATA_MISSING";
        }
        return "UNKNOWN_FAILURE";
    }

    private GetFasstoWarehousesResult mapWarehousesResult(FasstoWarehousesResponse response) {
        List<FasstoWarehouseInfoResult> warehouses = response.data().stream()
                .map(this::mapWarehouseInfo)
                .toList();
        Integer dataCount = response.header() != null ? response.header().dataCount() : null;
        return GetFasstoWarehousesResult.of(dataCount, warehouses);
    }

    private FasstoWarehouseInfoResult mapWarehouseInfo(FasstoWarehousesItemResponse item) {
        return FasstoWarehouseInfoResult.of(
                item.shopCd(),
                item.shopNm(),
                item.cstShopCd(),
                item.cstCd(),
                item.cstNm(),
                item.shopTp(),
                item.dealStrDt(),
                item.dealEndDt(),
                item.zipNo(),
                item.addr1(),
                item.addr2(),
                item.ceoNm(),
                item.busNo(),
                item.telNo(),
                item.unloadWay(),
                item.checkWay(),
                item.standYn(),
                item.formType(),
                item.empNm(),
                item.empPosit(),
                item.empTelNo(),
                item.useYn()
        );
    }
}
