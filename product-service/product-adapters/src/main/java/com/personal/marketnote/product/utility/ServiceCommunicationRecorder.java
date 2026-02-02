package com.personal.marketnote.product.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.product.domain.servicecommunication.ProductServiceCommunicationSenderType;
import com.personal.marketnote.product.domain.servicecommunication.ProductServiceCommunicationTargetType;
import com.personal.marketnote.product.domain.servicecommunication.ProductServiceCommunicationType;
import com.personal.marketnote.product.port.in.command.servicecommunication.ProductServiceCommunicationHistoryCommand;
import com.personal.marketnote.product.port.in.usecase.servicecommunication.RecordProductServiceCommunicationHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceCommunicationRecorder {
    private final RecordProductServiceCommunicationHistoryUseCase recordServiceCommunicationHistoryUseCase;

    public void record(
            ProductServiceCommunicationTargetType targetType,
            ProductServiceCommunicationType communicationType,
            ProductServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                ProductServiceCommunicationHistoryCommand.builder()
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
            ProductServiceCommunicationTargetType targetType,
            ProductServiceCommunicationType communicationType,
            ProductServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                ProductServiceCommunicationHistoryCommand.builder()
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
            ProductServiceCommunicationTargetType targetType,
            ProductServiceCommunicationType communicationType,
            ProductServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                ProductServiceCommunicationHistoryCommand.builder()
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
            ProductServiceCommunicationTargetType targetType,
            ProductServiceCommunicationType communicationType,
            ProductServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                ProductServiceCommunicationHistoryCommand.builder()
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
