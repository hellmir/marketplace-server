package com.personal.marketnote.reward.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorCommunicationTargetType;
import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorCommunicationType;
import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorName;
import com.personal.marketnote.reward.port.in.command.vendorcommunication.RewardVendorCommunicationHistoryCommand;
import com.personal.marketnote.reward.port.in.usecase.vendorcommunication.RewardRecordVendorCommunicationHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorCommunicationRecorder {
    private final RewardRecordVendorCommunicationHistoryUseCase recordVendorCommunicationHistoryUseCase;

    public void record(
            RewardVendorCommunicationTargetType targetType,
            RewardVendorCommunicationType communicationType,
            Long targetId,
            RewardVendorName vendorName,
            String payload,
            JsonNode payloadJson
    ) {
        recordVendorCommunicationHistoryUseCase.record(
                RewardVendorCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .targetId(targetId)
                        .vendorName(vendorName)
                        .communicationType(communicationType)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }

    public void record(
            RewardVendorCommunicationTargetType targetType,
            RewardVendorCommunicationType communicationType,
            RewardVendorName vendorName,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordVendorCommunicationHistoryUseCase.record(
                RewardVendorCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .vendorName(vendorName)
                        .communicationType(communicationType)
                        .exception(exception)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }
}
