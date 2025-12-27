package com.personal.marketnote.user.port.in.result;

import com.personal.marketnote.user.domain.user.User;

public record SignInResult(
        Long id,
        String roleId
) {
    public static SignInResult from(User user) {
        return new SignInResult(user.getId(), user.getRole().getId());
    }
}
