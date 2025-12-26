package com.personal.shop.user.port.in.usecase;

import com.personal.shop.user.port.in.result.GetUserResult;

public interface GetUserInfoUseCase {
    GetUserResult getUser(Long userId);
}
