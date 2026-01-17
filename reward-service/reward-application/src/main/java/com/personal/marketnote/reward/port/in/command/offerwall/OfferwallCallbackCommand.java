package com.personal.marketnote.reward.port.in.command.offerwall;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OfferwallCallbackCommand {
    private final OfferwallType offerwallType;
    private final String rewardKey;
    private final String userId;
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
        return FormatValidator.hasValue(adid);
    }

    public boolean isIos() {
        return FormatValidator.hasValue(idfa);
    }
}
