package com.personal.marketnote.reward.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.reward.domain.servicecommunication.RewardServiceCommunicationSenderType;
import com.personal.marketnote.reward.domain.servicecommunication.RewardServiceCommunicationTargetType;
import com.personal.marketnote.reward.domain.servicecommunication.RewardServiceCommunicationType;
import com.personal.marketnote.reward.port.in.command.servicecommunication.RewardServiceCommunicationHistoryCommand;
import com.personal.marketnote.reward.port.in.usecase.servicecommunication.RecordRewardServiceCommunicationHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceCommunicationRecorder {
    private final RecordRewardServiceCommunicationHistoryUseCase recordServiceCommunicationHistoryUseCase;

    public void record(
            RewardServiceCommunicationTargetType targetType,
            RewardServiceCommunicationType communicationType,
            RewardServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                RewardServiceCommunicationHistoryCommand.builder()
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
            RewardServiceCommunicationTargetType targetType,
            RewardServiceCommunicationType communicationType,
            RewardServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                RewardServiceCommunicationHistoryCommand.builder()
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
            RewardServiceCommunicationTargetType targetType,
            RewardServiceCommunicationType communicationType,
            RewardServiceCommunicationSenderType sender,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                RewardServiceCommunicationHistoryCommand.builder()
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
            RewardServiceCommunicationTargetType targetType,
            RewardServiceCommunicationType communicationType,
            RewardServiceCommunicationSenderType sender,
            String targetId,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordServiceCommunicationHistoryUseCase.record(
                RewardServiceCommunicationHistoryCommand.builder()
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
