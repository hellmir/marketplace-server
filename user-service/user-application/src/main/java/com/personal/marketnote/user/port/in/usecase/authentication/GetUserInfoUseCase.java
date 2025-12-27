package com.personal.marketnote.user.port.in.usecase.authentication;

import com.personal.marketnote.user.port.in.result.GetUserResult;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

public interface GetUserInfoUseCase {
    GetUserResult getUser(Long id);

    GetUserResult getUser(AuthVendor authVendor, String oidcId);

    GetUserResult getUser(String phoneNumber);
}
