package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.personal.marketnote.common.utility.FormatValidator;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterFasstoGoodsResponse(
        FasstoResponseHeader header,
        List<RegisterFasstoGoodsItemResponse> data,
        FasstoErrorInfo errorInfo
) implements FasstoApiResponse {
    public boolean isSuccess() {
        return FormatValidator.hasValue(header) && header.isSuccess() && FormatValidator.hasValue(data)
                && data.stream().allMatch(RegisterFasstoGoodsItemResponse::isSuccess);
    }

    @Override
    public String resolveErrorMessage() {
        String message = FasstoApiResponse.super.resolveErrorMessage();
        if (FormatValidator.hasValue(message)) {
            return message;
        }

        if (FormatValidator.hasValue(data)) {
            for (RegisterFasstoGoodsItemResponse item : data) {
                if (item == null || item.isSuccess()) {
                    continue;
                }
                String itemMessage = normalizeMessage(item.msg());
                if (FormatValidator.hasValue(itemMessage)) {
                    return itemMessage;
                }
            }
        }

        return null;
    }

    private static String normalizeMessage(String message) {
        if (FormatValidator.hasNoValue(message)) {
            return null;
        }

        String trimmed = message.trim();
        if ("null".equalsIgnoreCase(trimmed)) {
            return null;
        }

        String lower = trimmed.toLowerCase();
        if (lower.startsWith("null:")) {
            return trimmed.substring("null:".length()).trim();
        }

        return trimmed;
    }
}
