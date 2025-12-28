package com.personal.marketnote.user.port.in.usecase.user;

import com.personal.marketnote.user.port.in.command.UpdateUserInfoCommand;

public interface UpdateUserUseCase {
    void updateUserInfo(Long userId, UpdateUserInfoCommand updateUserInfoCommand);
}
