package com.personal.marketnote.fulfillment.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.fulfillment.domain.servicecommunication.FulfillmentServiceCommunicationSenderType;
import com.personal.marketnote.fulfillment.domain.servicecommunication.FulfillmentServiceCommunicationTargetType;
import com.personal.marketnote.fulfillment.domain.servicecommunication.FulfillmentServiceCommunicationType;
import com.personal.marketnote.fulfillment.port.in.command.servicecommunication.FulfillmentServiceCommunicationHistoryCommand;
import com.personal.marketnote.fulfillment.port.in.usecase.servicecommunication.RecordFulfillmentServiceCommunicationHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceCommunicationRecorder {
    private final RecordFulfillmentServiceCommunicationHistoryUseCase recordServiceCommunicationHistoryUseCase;

    public void record(
            FulfillmentServiceCommunicationTargetType targetType,
            FulfillmentServiceCommunicationType communicationType,
            FulfillmentServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                FulfillmentServiceCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .targetId(null)
                        .communicationType(communicationType)
                        .sender(sender)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }

    public void record(
            FulfillmentServiceCommunicationTargetType targetType,
            FulfillmentServiceCommunicationType communicationType,
            FulfillmentServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                FulfillmentServiceCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .targetId(targetId)
                        .communicationType(communicationType)
                        .sender(sender)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }

    public void record(
            FulfillmentServiceCommunicationTargetType targetType,
            FulfillmentServiceCommunicationType communicationType,
            FulfillmentServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                FulfillmentServiceCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .communicationType(communicationType)
                        .sender(sender)
                        .exception(exception)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }

    public void record(
            FulfillmentServiceCommunicationTargetType targetType,
            FulfillmentServiceCommunicationType communicationType,
            FulfillmentServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                FulfillmentServiceCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .targetId(targetId)
                        .communicationType(communicationType)
                        .sender(sender)
                        .exception(exception)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }
}
