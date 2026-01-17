package com.personal.marketnote.reward.port.in.usecase.point;

import com.personal.marketnote.reward.port.in.command.point.ModifyUserPointCommand;
import com.personal.marketnote.reward.port.in.result.point.UpdateUserPointResult;

public interface ModifyUserPointUseCase {
    UpdateUserPointResult modify(ModifyUserPointCommand command);
}
