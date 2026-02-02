package com.personal.marketnote.fulfillment.port.in.command.servicecommunication;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.fulfillment.domain.servicecommunication.FulfillmentServiceCommunicationSenderType;
import com.personal.marketnote.fulfillment.domain.servicecommunication.FulfillmentServiceCommunicationTargetType;
import com.personal.marketnote.fulfillment.domain.servicecommunication.FulfillmentServiceCommunicationType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FulfillmentServiceCommunicationHistoryCommand {
    private final FulfillmentServiceCommunicationTargetType targetType;
    private final String targetId;
    private final FulfillmentServiceCommunicationType communicationType;
    private final FulfillmentServiceCommunicationSenderType sender;
    private final String exception;
    private final String payload;
    private final JsonNode payloadJson;
}
