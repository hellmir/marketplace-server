package com.personal.marketnote.reward.service.offerwall;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.reward.domain.offerwall.OfferwallType;
import com.personal.marketnote.reward.port.in.usecase.offerwall.GetPostOfferwallMapperUseCase;
import com.personal.marketnote.reward.port.out.offerwall.FindOfferwallMapperPort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetOfferwallMapperService implements GetPostOfferwallMapperUseCase {
    private final FindOfferwallMapperPort findOfferwallMapperPort;

    @Override
    public boolean existsOfferwallMapper(OfferwallType offerwallType, String rewardKey) {
        return findOfferwallMapperPort.existsByOfferwallTypeAndRewardKey(offerwallType, rewardKey);
    }
}
