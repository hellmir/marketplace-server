package com.personal.marketnote.fulfillment.adapter.out.vendor.fassto.response;

import com.personal.marketnote.common.utility.FormatValidator;

public interface FasstoApiResponse {
    FasstoResponseHeader header();

    FasstoErrorInfo errorInfo();

    default String resolveErrorMessage() {
        String message = null;
        if (FormatValidator.hasValue(errorInfo()) && FormatValidator.hasValue(errorInfo().errorMessage())) {
            message = errorInfo().errorMessage();
        } else if (FormatValidator.hasValue(header()) && !header().isSuccess()) {
            message = header().msg();
        }

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
