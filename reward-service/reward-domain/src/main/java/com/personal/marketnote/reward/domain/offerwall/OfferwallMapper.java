package com.personal.marketnote.reward.domain.offerwall;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class OfferwallMapper {
    private Long id;
    private OfferwallType offerwallType;
    private String rewardKey;
    private String userKey;
    private String campaignKey;
    private Integer campaignType;
    private String campaignName;
    private Long quantity;
    private String signedValue;
    private Integer appKey;
    private String appName;
    private String adid;
    private String idfa;
    private Boolean isSuccess;
    private LocalDateTime attendedAt;
    private String requestPayload;
    private JsonNode requestPayloadJson;
    private String responsePayload;
    private JsonNode responsePayloadJson;
    private LocalDateTime createdAt;

    public static OfferwallMapper from(OfferwallMapperCreateState state) {
        return OfferwallMapper.builder()
                .offerwallType(state.getOfferwallType())
                .rewardKey(state.getRewardKey())
                .userKey(state.getUserKey())
                .campaignKey(state.getCampaignKey())
                .campaignType(state.getCampaignType())
                .campaignName(state.getCampaignName())
                .quantity(state.getQuantity())
                .signedValue(state.getSignedValue())
                .appKey(state.getAppKey())
                .appName(state.getAppName())
                .adid(state.getAdid())
                .idfa(state.getIdfa())
                .isSuccess(state.getIsSuccess() != null ? state.getIsSuccess() : Boolean.TRUE)
                .attendedAt(state.getAttendedAt())
                .requestPayload(state.getRequestPayload())
                .requestPayloadJson(state.getRequestPayloadJson())
                .responsePayload(state.getResponsePayload())
                .responsePayloadJson(state.getResponsePayloadJson())
                .build();
    }

    public static OfferwallMapper from(OfferwallMapperSnapshotState state) {
        return OfferwallMapper.builder()
                .id(state.getId())
                .offerwallType(state.getOfferwallType())
                .rewardKey(state.getRewardKey())
                .userKey(state.getUserKey())
                .campaignKey(state.getCampaignKey())
                .campaignType(state.getCampaignType())
                .campaignName(state.getCampaignName())
                .quantity(state.getQuantity())
                .signedValue(state.getSignedValue())
                .appKey(state.getAppKey())
                .appName(state.getAppName())
                .adid(state.getAdid())
                .idfa(state.getIdfa())
                .isSuccess(state.getIsSuccess())
                .attendedAt(state.getAttendedAt())
                .requestPayload(state.getRequestPayload())
                .requestPayloadJson(state.getRequestPayloadJson())
                .responsePayload(state.getResponsePayload())
                .responsePayloadJson(state.getResponsePayloadJson())
                .createdAt(state.getCreatedAt())
                .build();
    }
}
