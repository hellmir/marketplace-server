package com.personal.marketnote.reward.port.out.offerwall;

import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.domain.offerwall.OfferwallType;

import java.util.Optional;

public interface FindOfferwallMapperPort {
    boolean existsByOfferwallTypeAndRewardKeyAndIsSuccess(OfferwallType offerwallType, String rewardKey, boolean isSuccess);

    Optional<OfferwallMapper> findTopFailedOfferwallMapper(OfferwallType offerwallType, String rewardKey);
}
