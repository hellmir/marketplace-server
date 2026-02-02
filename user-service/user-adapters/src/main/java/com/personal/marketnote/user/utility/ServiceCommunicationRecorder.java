package com.personal.marketnote.user.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.user.domain.servicecommunication.UserServiceCommunicationSenderType;
import com.personal.marketnote.user.domain.servicecommunication.UserServiceCommunicationTargetType;
import com.personal.marketnote.user.domain.servicecommunication.UserServiceCommunicationType;
import com.personal.marketnote.user.port.in.command.servicecommunication.UserServiceCommunicationHistoryCommand;
import com.personal.marketnote.user.port.in.usecase.servicecommunication.RecordUserServiceCommunicationHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceCommunicationRecorder {
    private final RecordUserServiceCommunicationHistoryUseCase recordServiceCommunicationHistoryUseCase;

    public void record(
            UserServiceCommunicationTargetType targetType,
            UserServiceCommunicationType communicationType,
            UserServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                UserServiceCommunicationHistoryCommand.builder()
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
            UserServiceCommunicationTargetType targetType,
            UserServiceCommunicationType communicationType,
            UserServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                UserServiceCommunicationHistoryCommand.builder()
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
            UserServiceCommunicationTargetType targetType,
            UserServiceCommunicationType communicationType,
            UserServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                UserServiceCommunicationHistoryCommand.builder()
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
            UserServiceCommunicationTargetType targetType,
            UserServiceCommunicationType communicationType,
            UserServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                UserServiceCommunicationHistoryCommand.builder()
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
