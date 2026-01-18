package com.personal.marketnote.reward.adapter.in.offerwall.mapper;

import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.domain.offerwall.UserDeviceType;
import com.personal.marketnote.reward.port.in.command.offerwall.RegisterOfferwallRewardCommand;

import java.time.LocalDateTime;

public class RewardRequestToCommandMapper {
    public static RegisterOfferwallRewardCommand mapToOfferwallCallbackCommand(
            OfferwallType offerwallType,
            String rewardKey,
            String userKey,
            UserDeviceType userDeviceType,
            String campaignKey,
            Integer campaignType,
            String campaignName,
            Long quantity,
            String signedValue,
            Integer appKey,
            String appName,
            String adid,
            String idfa,
            LocalDateTime attendedAt
    ) {
        return RegisterOfferwallRewardCommand.builder()
                .offerwallType(offerwallType)
                .rewardKey(rewardKey)
                .userId(userKey)
                .userDeviceType(userDeviceType)
                .campaignKey(campaignKey)
                .campaignType(campaignType)
                .campaignName(campaignName)
                .quantity(quantity)
                .signedValue(signedValue)
                .appKey(appKey)
                .appName(appName)
                .adid(adid)
                .idfa(idfa)
                .attendedAt(attendedAt)
                .build();
    }
}
