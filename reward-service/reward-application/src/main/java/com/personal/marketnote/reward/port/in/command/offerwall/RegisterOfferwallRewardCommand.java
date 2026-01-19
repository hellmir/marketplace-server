package com.personal.marketnote.reward.port.in.command.offerwall;

import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.domain.offerwall.UserDeviceType;
import com.personal.marketnote.reward.exception.InvalidOfferwallTypeException;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RegisterOfferwallRewardCommand(
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
    public boolean isAdpopcorn() {
        return offerwallType.isAdpopcorn();
    }

    public boolean isTnk() {
        return offerwallType.isTnk();
    }

    public boolean isAndroid() {
        return userDeviceType.isAndroid();
    }

    public boolean isIos() {
        return userDeviceType.isIos();
    }

    public String buildPlainText() {
        if (offerwallType.isAdpopcorn()) {
            return userKey + rewardKey + quantity + campaignKey;
        }

        if (offerwallType.isTnk()) {
            return appKey + userKey + rewardKey;
        }

        throw new InvalidOfferwallTypeException(offerwallType.name());
    }
}
