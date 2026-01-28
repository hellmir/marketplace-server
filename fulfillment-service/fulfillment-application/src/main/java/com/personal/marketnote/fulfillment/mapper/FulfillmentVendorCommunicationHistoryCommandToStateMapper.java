package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationHistoryCreateState;
import com.personal.marketnote.fulfillment.port.in.command.vendorcommunication.FulfillmentVendorCommunicationHistoryCommand;

public class FulfillmentVendorCommunicationHistoryCommandToStateMapper {
    private FulfillmentVendorCommunicationHistoryCommandToStateMapper() {
    }

    public static FulfillmentVendorCommunicationHistoryCreateState mapToCreateState(
            FulfillmentVendorCommunicationHistoryCommand command
    ) {
        return FulfillmentVendorCommunicationHistoryCreateState.builder()
                .targetType(command.getTargetType())
                .targetId(command.getTargetId())
                .vendorName(command.getVendorName())
                .communicationType(command.getCommunicationType())
                .sender(command.getSender())
                .exception(command.getException())
                .payload(command.getPayload())
                .payloadJson(command.getPayloadJson())
                .build();
    }
}
