package com.personal.marketnote.fulfillment.port.in.command.vendorcommunication;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationSenderType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationTargetType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorName;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FulfillmentVendorCommunicationHistoryCommand {
    private final FulfillmentVendorCommunicationTargetType targetType;
    private final String targetId;
    private final FulfillmentVendorName vendorName;
    private final FulfillmentVendorCommunicationType communicationType;
    private final FulfillmentVendorCommunicationSenderType sender;
    private final String exception;
    private final String payload;
    private final JsonNode payloadJson;
}
