package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoginHistory {
    private Long id;
    private final User user;
    private final AuthVendor authVendor;
    private final String ipAddress;
    private LocalDateTime createdAt;

    private LoginHistory(Long id, User user, AuthVendor authVendor, String ipAddress, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.authVendor = authVendor;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
    }

    private LoginHistory(User user, AuthVendor authVendor, String ipAddress) {
        this.user = user;
        this.authVendor = authVendor;
        this.ipAddress = ipAddress;
    }

    public static LoginHistory of(User user, AuthVendor authVendor, String ipAddress) {
        return new LoginHistory(user, authVendor, ipAddress);
    }

    public static LoginHistory of(Long id, User user, AuthVendor authVendor, String ipAddress, LocalDateTime createdAt) {
        return new LoginHistory(id, user, authVendor, ipAddress, createdAt);
    }
}
