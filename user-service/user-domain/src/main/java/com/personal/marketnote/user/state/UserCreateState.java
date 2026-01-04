package com.personal.marketnote.user.state;

import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class UserCreateState {
    private final AuthVendor targetAuthVendor;
    private final String oidcId;
    private final AuthVendor targetAuthVendor;
    private final AuthVendor targetAuthVendor;
    private final String email;
    private final String password;
    private final String verificationCode;
    private final String nickname;
    private final String fullName;
    private final String phoneNumber;
}
