package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHistoryCreateState {
    private final User user;
    private final AuthVendor authVendor;
    private final String ipAddress;
}

