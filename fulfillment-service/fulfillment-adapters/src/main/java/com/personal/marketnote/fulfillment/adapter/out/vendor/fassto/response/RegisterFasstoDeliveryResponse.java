package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.personal.marketnote.common.utility.FormatValidator;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterFasstoDeliveryResponse(
        FasstoResponseHeader header,
        @JsonDeserialize(using = RegisterFasstoDeliveryDataDeserializer.class)
        List<RegisterFasstoDeliveryItemResponse> data,
        FasstoErrorInfo errorInfo
) implements FasstoApiResponse {
    public boolean isSuccess() {
        if (FormatValidator.hasNoValue(header) || !header.isSuccess()) {
            return false;
        }
        if (FormatValidator.hasNoValue(data)) {
            return true;
        }
        return data.stream().allMatch(RegisterFasstoDeliveryItemResponse::isSuccess);
    }

    @Override
    public String resolveErrorMessage() {
        String message = FasstoApiResponse.super.resolveErrorMessage();
        if (FormatValidator.hasValue(message)) {
            return message;
        }

        if (FormatValidator.hasValue(data)) {
            for (RegisterFasstoDeliveryItemResponse item : data) {
                if (FormatValidator.hasNoValue(item) || item.isSuccess()) {
                    continue;
                }

                String itemMessage = FasstoApiResponse.normalizeMessage(item.msg());
                if (FormatValidator.hasValue(itemMessage)) {
                    return itemMessage;
                }
            }
        }

        return null;
    }
}
