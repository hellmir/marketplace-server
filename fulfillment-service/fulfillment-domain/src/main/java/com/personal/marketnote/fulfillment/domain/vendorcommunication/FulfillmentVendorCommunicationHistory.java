package com.personal.marketnote.fulfillment.domain.vendorcommunication;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class FulfillmentVendorCommunicationHistory {
    private Long id;
    private FulfillmentVendorCommunicationTargetType targetType;
    private Long targetId;
    private FulfillmentVendorName vendorName;
    private FulfillmentVendorCommunicationType communicationType;
    private String exception;
    private String payload;
    private JsonNode payloadJson;
    private LocalDateTime createdAt;

    public static FulfillmentVendorCommunicationHistory from(FulfillmentVendorCommunicationHistoryCreateState state) {
        return FulfillmentVendorCommunicationHistory.builder()
                .targetType(state.getTargetType())
                .targetId(state.getTargetId())
                .vendorName(state.getVendorName())
                .communicationType(state.getCommunicationType())
                .exception(state.getException())
                .payload(state.getPayload())
                .payloadJson(state.getPayloadJson())
                .build();
    }

    public static FulfillmentVendorCommunicationHistory from(FulfillmentVendorCommunicationHistorySnapshotState state) {
        return FulfillmentVendorCommunicationHistory.builder()
                .id(state.getId())
                .targetType(state.getTargetType())
                .targetId(state.getTargetId())
                .vendorName(state.getVendorName())
                .communicationType(state.getCommunicationType())
                .exception(state.getException())
                .payload(state.getPayload())
                .payloadJson(state.getPayloadJson())
                .createdAt(state.getCreatedAt())
                .build();
    }
}
