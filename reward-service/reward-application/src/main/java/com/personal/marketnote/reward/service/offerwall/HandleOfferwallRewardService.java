package com.personal.marketnote.reward.service.offerwall;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.mapper.RewardCommandToStateMapper;
import com.personal.marketnote.reward.port.in.command.offerwall.RegisterOfferwallRewardCommand;
import com.personal.marketnote.reward.port.in.usecase.offerwall.HandleOfferwallRewardUseCase;
import com.personal.marketnote.reward.port.in.usecase.offerwall.RegisterOfferwallRewardUseCase;
import com.personal.marketnote.reward.port.out.offerwall.SaveOfferwallMapperPort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class HandleOfferwallRewardService implements HandleOfferwallRewardUseCase {
    private final RegisterOfferwallRewardUseCase registerOfferwallRewardUseCase;
    private final SaveOfferwallMapperPort saveOfferwallMapperPort;

    @Override
    public Long handle(RegisterOfferwallRewardCommand command) {
        try {
            return registerOfferwallRewardUseCase.register(command);
        } catch (Exception e) {
            saveOfferwallMapperPort.save(
                    OfferwallMapper.from(RewardCommandToStateMapper.mapToOfferwallMapperCreateState(command, false))
            );

            throw e;
        }
    }
}
