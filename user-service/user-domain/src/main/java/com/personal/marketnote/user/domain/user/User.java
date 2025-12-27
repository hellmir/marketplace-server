package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.user.domain.authentication.Role;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class User {
    private Long id;
    private AuthVendor authVendor;
    private String oidcId;
    private String nickname;
    private String fullName;
    private String phoneNumber;
    private String referenceCode;
    private Role role;
    private LocalDateTime lastLoggedInAt;

    public static User of(AuthVendor authVendor, String oidcId) {
        return User.builder()
                .authVendor(authVendor)
                .oidcId(oidcId)
                .role(Role.getGuest())
                .build();
    }

    public static User of(AuthVendor authVendor, String oidcId, String nickname, String fullName, String phoneNumber) {
        return User.builder()
                .authVendor(authVendor)
                .oidcId(oidcId)
                .nickname(nickname)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .role(Role.getBuyer())
                .lastLoggedInAt(LocalDateTime.now())
                .build();
    }

    public static User of(
            Long id,
            AuthVendor authVendor,
            String oidcId,
            String nickname,
            String fullName,
            String phoneNumber,
            String referenceCode,
            Role role,
            LocalDateTime lastLoggedInAt
    ) {
        return User.builder()
                .id(id)
                .authVendor(authVendor)
                .oidcId(oidcId)
                .nickname(nickname)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .referenceCode(referenceCode)
                .role(role)
                .lastLoggedInAt(lastLoggedInAt)
                .build();
    }

    public boolean isGuest() {
        return role.isGuest();
    }
}
