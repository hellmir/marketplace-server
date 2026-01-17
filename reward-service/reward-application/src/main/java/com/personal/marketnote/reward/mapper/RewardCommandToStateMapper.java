package com.personal.marketnote.reward.mapper;

import com.personal.marketnote.reward.domain.offerwall.OfferwallMapperCreateState;
import com.personal.marketnote.reward.port.in.command.offerwall.OfferwallCallbackCommand;

public class RewardCommandToStateMapper {

    public static OfferwallMapperCreateState mapToOfferwallMapperCreateState(OfferwallCallbackCommand command) {
        return OfferwallMapperCreateState.builder()
                .offerwallType(command.getOfferwallType())
                .rewardKey(command.getRewardKey())
                .userId(command.getUserId())
                .userDeviceType(command.getUserDeviceType())
                .campaignKey(command.getCampaignKey())
                .campaignType(command.getCampaignType())
                .campaignName(command.getCampaignName())
                .quantity(command.getQuantity())
                .signedValue(command.getSignedValue())
                .appKey(command.getAppKey())
                .appName(command.getAppName())
                .adid(command.getAdid())
                .idfa(command.getIdfa())
                .isSuccess(command.getIsSuccess())
                .attendedAt(command.getAttendedAt())
                .build();
    }
}
