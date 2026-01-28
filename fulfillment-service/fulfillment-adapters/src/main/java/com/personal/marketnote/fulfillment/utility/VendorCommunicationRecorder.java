package com.personal.marketnote.fulfillment.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationSenderType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationTargetType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationType;
import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorName;
import com.personal.marketnote.fulfillment.port.in.command.vendorcommunication.FulfillmentVendorCommunicationHistoryCommand;
import com.personal.marketnote.fulfillment.port.in.usecase.vendorcommunication.RecordFulfillmentVendorCommunicationHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorCommunicationRecorder {
    private final RecordFulfillmentVendorCommunicationHistoryUseCase recordVendorCommunicationHistoryUseCase;

    public void record(
            FulfillmentVendorCommunicationTargetType targetType,
            FulfillmentVendorCommunicationType communicationType,
            FulfillmentVendorCommunicationSenderType sender,
            FulfillmentVendorName vendorName,
            String payload,
            JsonNode payloadJson
    ) {
        recordVendorCommunicationHistoryUseCase.record(
                FulfillmentVendorCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .targetId(null)
                        .vendorName(vendorName)
                        .communicationType(communicationType)
                        .sender(sender)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }

    public void record(
            FulfillmentVendorCommunicationTargetType targetType,
            FulfillmentVendorCommunicationType communicationType,
            FulfillmentVendorCommunicationSenderType sender,
            String targetId,
            FulfillmentVendorName vendorName,
            String payload,
            JsonNode payloadJson
    ) {
        recordVendorCommunicationHistoryUseCase.record(
                FulfillmentVendorCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .targetId(targetId)
                        .vendorName(vendorName)
                        .communicationType(communicationType)
                        .sender(sender)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }

    public void record(
            FulfillmentVendorCommunicationTargetType targetType,
            FulfillmentVendorCommunicationType communicationType,
            FulfillmentVendorCommunicationSenderType sender,
            FulfillmentVendorName vendorName,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordVendorCommunicationHistoryUseCase.record(
                FulfillmentVendorCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .vendorName(vendorName)
                        .communicationType(communicationType)
                        .sender(sender)
                        .exception(exception)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }

    public void record(
            FulfillmentVendorCommunicationTargetType targetType,
            FulfillmentVendorCommunicationType communicationType,
            FulfillmentVendorCommunicationSenderType sender,
            String targetId,
            FulfillmentVendorName vendorName,
            String payload,
            JsonNode payloadJson,
            String exception
    ) {
        recordVendorCommunicationHistoryUseCase.record(
                FulfillmentVendorCommunicationHistoryCommand.builder()
                        .targetType(targetType)
                        .targetId(targetId)
                        .vendorName(vendorName)
                        .communicationType(communicationType)
                        .sender(sender)
                        .exception(exception)
                        .payload(payload)
                        .payloadJson(payloadJson)
                        .build()
        );
    }
}
