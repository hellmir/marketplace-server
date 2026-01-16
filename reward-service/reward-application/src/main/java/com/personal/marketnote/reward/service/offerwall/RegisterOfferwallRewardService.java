package com.personal.marketnote.reward.service.offerwall;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.mapper.RewardCommandToStateMapper;
import com.personal.marketnote.reward.port.in.command.offerwall.OfferwallCallbackCommand;
import com.personal.marketnote.reward.port.in.usecase.offerwall.RegisterOfferwallRewardUseCase;
import com.personal.marketnote.reward.port.out.offerwall.SaveOfferwallMapperPort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RegisterOfferwallRewardService implements RegisterOfferwallRewardUseCase {
    private final SaveOfferwallMapperPort saveOfferwallMapperPort;

    @Override
    public OfferwallMapper register(OfferwallCallbackCommand command) {
        return saveOfferwallMapperPort.save(
                OfferwallMapper.from(RewardCommandToStateMapper.mapToOfferwallMapperCreateState(command))
        );
    }
}
