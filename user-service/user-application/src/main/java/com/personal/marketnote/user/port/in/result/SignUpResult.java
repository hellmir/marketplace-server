package com.personal.marketnote.user.port.in.result;

import com.personal.marketnote.user.domain.user.User;

public record SignUpResult(
        Long id,
        String roleId,
        boolean isNewUser
) {
    public static SignUpResult from(User user, boolean isNewUser) {
        return new SignUpResult(user.getId(), user.getRole().getId(), isNewUser);
    }
}
