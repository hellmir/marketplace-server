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
