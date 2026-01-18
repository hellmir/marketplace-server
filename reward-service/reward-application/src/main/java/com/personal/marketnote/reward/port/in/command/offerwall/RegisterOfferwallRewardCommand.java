package com.personal.marketnote.reward.port.in.command.offerwall;

import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.domain.offerwall.UserDeviceType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RegisterOfferwallRewardCommand(
        OfferwallType offerwallType,
        String rewardKey,
        String userId,
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
    public boolean isAndroid() {
        return userDeviceType.isAndroid();
    }

    public boolean isIos() {
        return userDeviceType.isIos();
    }
}
