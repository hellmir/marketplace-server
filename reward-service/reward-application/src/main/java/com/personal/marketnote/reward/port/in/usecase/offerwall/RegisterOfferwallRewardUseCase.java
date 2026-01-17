package com.personal.marketnote.reward.port.in.usecase.offerwall;

import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.port.in.command.offerwall.RegisterOfferwallRewardCommand;

public interface RegisterOfferwallRewardUseCase {
    OfferwallMapper register(RegisterOfferwallRewardCommand command);
}
