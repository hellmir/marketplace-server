package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoginHistory {
    private Long id;
    private final User user;
    private final AuthVendor type;
    private final LocalDateTime createdAt;

    private LoginHistory(User user, AuthVendor type, LocalDateTime createdAt) {
        this.user = user;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static LoginHistory of(User user, AuthVendor type) {
        return new LoginHistory(user, type, LocalDateTime.now());
    }
}
