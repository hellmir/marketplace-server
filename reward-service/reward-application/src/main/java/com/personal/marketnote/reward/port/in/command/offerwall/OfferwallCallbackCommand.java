package com.personal.marketnote.reward.port.in.command.offerwall;

import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.domain.offerwall.UserDeviceType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OfferwallCallbackCommand {
    private final OfferwallType offerwallType;
    private final String rewardKey;
    private final String userId;
    private final UserDeviceType userDeviceType;
    private final String campaignKey;
    private final Integer campaignType;
    private final String campaignName;
    private final Long quantity;
    private final String signedValue;
    private final Integer appKey;
    private final String appName;
    private final String adid;
    private final String idfa;
    private final Boolean isSuccess;
    private final LocalDateTime attendedAt;

    public boolean isAndroid() {
        return userDeviceType.isAndroid();
    }

    public boolean isIos() {
        return userDeviceType.isIos();
    }
}
