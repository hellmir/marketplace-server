package com.personal.marketnote.reward.port.in.command.offerwall;

import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.domain.offerwall.UserDeviceType;
import com.personal.marketnote.reward.exception.InvalidOfferwallTypeException;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RegisterOfferwallRewardCommand(
        OfferwallType offerwallType,
        String rewardUnit,
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

    public boolean isAdiscope() {
        return offerwallType.isAdiscope();
    }

    public boolean isAndroid() {
        return userDeviceType.isAndroid();
    }

    public boolean isIos() {
        return userDeviceType.isIos();
    }

    public String buildPlainText(String hashKey) {
        if (isAdpopcorn()) {
            return userKey + rewardKey + quantity + campaignKey;
        }

        if (isTnk()) {
            return hashKey + userKey + rewardKey;
        }

        if (isAdiscope()) {
            return userKey + rewardUnit + quantity + rewardKey;
        }

        throw new InvalidOfferwallTypeException(offerwallType.name());
    }
}
