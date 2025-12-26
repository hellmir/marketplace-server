package com.personal.shop.user.domain.user;

import com.personal.shop.user.security.token.vendor.AuthVendor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoginHistory {
    private Long id;
    private User user;
    private AuthVendor type;
    private LocalDateTime createdAt;

    private LoginHistory(User user, AuthVendor type, LocalDateTime createdAt) {
        this.user = user;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static LoginHistory of(User user, AuthVendor type) {
        return new LoginHistory(user, type, LocalDateTime.now());
    }
}
