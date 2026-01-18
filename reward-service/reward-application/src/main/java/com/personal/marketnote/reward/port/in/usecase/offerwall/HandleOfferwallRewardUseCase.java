package com.personal.marketnote.reward.port.in.usecase.offerwall;

import com.personal.marketnote.reward.port.in.command.offerwall.RegisterOfferwallRewardCommand;

public interface HandleOfferwallRewardUseCase {
    Long handle(RegisterOfferwallRewardCommand command);
}
