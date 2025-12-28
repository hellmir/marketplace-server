package com.personal.marketnote.user.port.in.result;

import com.personal.marketnote.user.domain.user.User;

import java.time.LocalDateTime;

public record GetUserResult(
        Long id,
        String authVendor,
        String oidcId,
        String nickname,
        String email,
        String fullName,
        String phoneNumber,
        String referenceCode,
        String roleId,
        LocalDateTime lastLoggedInAt
) {
    public static GetUserResult from(User user) {
        return new GetUserResult(
                user.getId(),
                user.getAuthVendor().name(),
                user.getOidcId(),
                user.getNickname(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getReferenceCode(),
                user.getRole().getId(),
                user.getLastLoggedInAt()
        );
    }
}
