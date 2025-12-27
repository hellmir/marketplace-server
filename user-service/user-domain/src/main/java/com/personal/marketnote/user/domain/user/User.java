package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.user.domain.authentication.Role;
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
    private String oidcId;
    private String nickname;
    private String fullName;
    private String phoneNumber;
    private String referenceCode;
    private Role role;
    private LocalDateTime lastLoggedInAt;

    public static User of(String oidcId) {
        return User.builder()
                .oidcId(oidcId)
                .role(Role.getGuest())
                .build();
    }

    public static User of(String oidcId, String nickname, String fullName, String phoneNumber) {
        return User.builder()
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
