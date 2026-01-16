package com.personal.marketnote.reward.adapter.in.offerwall.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.port.in.command.offerwall.OfferwallCallbackCommand;

import java.time.LocalDateTime;

public class RewardRequestToCommandMapper {

    public static OfferwallCallbackCommand mapToOfferwallCallbackCommand(
            OfferwallType offerwallType,
            String rewardKey,
            String userKey,
            String campaignKey,
            Integer campaignType,
            String campaignName,
            Long quantity,
            String signedValue,
            Integer appKey,
            String appName,
            String adid,
            String idfa,
            LocalDateTime attendedAt,
            Boolean isSuccess,
            String requestPayload,
            JsonNode requestPayloadJson
    ) {
        return OfferwallCallbackCommand.builder()
                .offerwallType(offerwallType)
                .rewardKey(rewardKey)
                .userKey(userKey)
                .campaignKey(campaignKey)
                .campaignType(campaignType)
                .campaignName(campaignName)
                .quantity(quantity)
                .signedValue(signedValue)
                .appKey(appKey)
                .appName(appName)
                .adid(adid)
                .idfa(idfa)
                .isSuccess(isSuccess)
                .attendedAt(attendedAt)
                .requestPayload(requestPayload)
                .requestPayloadJson(requestPayloadJson)
                .build();
    }
}
