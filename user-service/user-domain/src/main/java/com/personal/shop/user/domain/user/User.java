package com.personal.shop.user.domain.user;

import com.personal.shop.user.domain.authentication.Role;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long id;
    private String oidcId;
    private String nickname;
    private String referenceCode;
    private Role role;
    private LocalDateTime lastLoggedInAt;

    private User(String oidcId, Role role) {
        this.oidcId = oidcId;
        this.role = role;
    }

    private User(String oidcId, String nickname, Role role) {
        this.oidcId = oidcId;
        this.nickname = nickname;
        this.role = role;
        lastLoggedInAt = LocalDateTime.now();
    }

    public static User of(String oidcId) {
        return new User(oidcId, Role.getGuest());
    }

    public static User of(String oidcId, String nickname) {
        return new User(nickname, oidcId, Role.getBuyer());
    }

    public static User of(
            Long id,
            String oidcId,
            String nickname,
            String referenceCode,
            Role role,
            LocalDateTime lastLoggedInAt
    ) {
        return new User(oidcId, nickname, role);
    }

    public boolean isGuest() {
        return role.isGuest();
    }
}
