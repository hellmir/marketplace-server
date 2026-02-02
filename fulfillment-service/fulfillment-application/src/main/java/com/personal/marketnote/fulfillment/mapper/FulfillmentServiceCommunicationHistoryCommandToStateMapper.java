package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.servicecommunication.FulfillmentServiceCommunicationHistoryCreateState;
import com.personal.marketnote.fulfillment.port.in.command.servicecommunication.FulfillmentServiceCommunicationHistoryCommand;

public class FulfillmentServiceCommunicationHistoryCommandToStateMapper {
    private FulfillmentServiceCommunicationHistoryCommandToStateMapper() {
    }

    public static FulfillmentServiceCommunicationHistoryCreateState mapToCreateState(
            FulfillmentServiceCommunicationHistoryCommand command
    ) {
        return FulfillmentServiceCommunicationHistoryCreateState.builder()
                .targetType(command.getTargetType())
                .targetId(command.getTargetId())
                .communicationType(command.getCommunicationType())
                .sender(command.getSender())
                .exception(command.getException())
                .payload(command.getPayload())
                .payloadJson(command.getPayloadJson())
                .build();
    }
}
