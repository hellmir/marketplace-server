package com.personal.marketnote.reward.port.in.usecase.offerwall;

import com.personal.marketnote.reward.port.in.command.offerwall.OfferwallCallbackCommand;

public interface RegisterOfferwallRewardUseCase {
    String register(OfferwallCallbackCommand command);
}
