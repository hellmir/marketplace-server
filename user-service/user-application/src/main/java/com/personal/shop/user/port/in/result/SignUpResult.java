package com.personal.shop.user.port.in.result;

import com.personal.shop.user.domain.user.User;

public record SignUpResult(
        Long id,
        String roleId
) {
    public static SignUpResult from(User user) {
        return new SignUpResult(user.getId(), user.getRole().getId());
    }
}
