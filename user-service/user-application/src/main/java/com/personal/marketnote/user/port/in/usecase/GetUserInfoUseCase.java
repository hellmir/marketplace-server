package com.personal.marketnote.user.port.in.usecase;

import com.personal.marketnote.user.port.in.result.GetUserResult;

public interface GetUserInfoUseCase {
    GetUserResult getUser(Long userId);
}
