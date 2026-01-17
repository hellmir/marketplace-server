package com.personal.marketnote.reward.port.out.offerwall;

import com.personal.marketnote.reward.domain.offerwall.OfferwallType;

public interface FindOfferwallMapperPort {
    boolean existsByOfferwallTypeAndRewardKey(OfferwallType offerwallType, String rewardKey);
}
