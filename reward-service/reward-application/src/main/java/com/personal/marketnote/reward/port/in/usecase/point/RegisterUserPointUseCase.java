package com.personal.marketnote.reward.port.in.usecase.point;

import com.personal.marketnote.reward.port.in.command.point.RegisterUserPointCommand;

public interface RegisterUserPointUseCase {
    void register(RegisterUserPointCommand command);
}
