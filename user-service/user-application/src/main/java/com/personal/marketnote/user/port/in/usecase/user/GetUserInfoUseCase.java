package com.personal.marketnote.user.port.in.usecase.user;

import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.in.result.GetUserResult;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

public interface GetUserInfoUseCase {
    User getUser(Long id);

    User getUser(AuthVendor authVendor, String oidcId);

    User getUser(String email);

    GetUserResult getUserInfo(Long id);
}
