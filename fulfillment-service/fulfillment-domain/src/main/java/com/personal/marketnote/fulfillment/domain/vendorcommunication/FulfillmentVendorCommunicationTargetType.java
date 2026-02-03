package com.personal.marketnote.fulfillment.domain.vendorcommunication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FulfillmentVendorCommunicationTargetType {
    AUTHENTICATION("인증"),
    SHOP("출고처"),
    SUPPLIER("공급사"),
    GOODS("상품"),
    WAREHOUSING("입고"),
    STOCK("재고");

    private final String description;
}
