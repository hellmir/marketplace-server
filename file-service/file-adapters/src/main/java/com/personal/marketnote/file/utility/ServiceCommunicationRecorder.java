package com.personal.marketnote.file.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.file.domain.servicecommunication.FileServiceCommunicationSenderType;
import com.personal.marketnote.file.domain.servicecommunication.FileServiceCommunicationTargetType;
import com.personal.marketnote.file.domain.servicecommunication.FileServiceCommunicationType;
import com.personal.marketnote.file.port.in.command.servicecommunication.FileServiceCommunicationHistoryCommand;
import com.personal.marketnote.file.port.in.usecase.servicecommunication.RecordFileServiceCommunicationHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceCommunicationRecorder {
    private final RecordFileServiceCommunicationHistoryUseCase recordServiceCommunicationHistoryUseCase;

    public void record(
            FileServiceCommunicationTargetType targetType,
            FileServiceCommunicationType communicationType,
            FileServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                FileServiceCommunicationHistoryCommand.builder()
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
            FileServiceCommunicationTargetType targetType,
            FileServiceCommunicationType communicationType,
            FileServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                FileServiceCommunicationHistoryCommand.builder()
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
            FileServiceCommunicationTargetType targetType,
            FileServiceCommunicationType communicationType,
            FileServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                FileServiceCommunicationHistoryCommand.builder()
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
            FileServiceCommunicationTargetType targetType,
            FileServiceCommunicationType communicationType,
            FileServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                FileServiceCommunicationHistoryCommand.builder()
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
