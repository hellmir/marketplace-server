package com.personal.marketnote.fulfillment.domain.vendorcommunication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FulfillmentVendorCommunicationTargetType {
    AUTHENTICATION("인증");

    private final String description;
}
