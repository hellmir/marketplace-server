package com.personal.marketnote.fulfillment.domain.vendorcommunication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FulfillmentVendorCommunicationTargetType {
    AUTHENTICATION("인증"),
    WAREHOUSE("출고처");

    private final String description;
}
