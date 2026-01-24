package com.personal.marketnote.fulfillment.domain.vendorcommunication;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FulfillmentVendorCommunicationHistoryCreateState {
    private final FulfillmentVendorCommunicationTargetType targetType;
    private final Long targetId;
    private final FulfillmentVendorName vendorName;
    private final FulfillmentVendorCommunicationType communicationType;
    private final String exception;
    private final String payload;
    private final JsonNode payloadJson;
}
