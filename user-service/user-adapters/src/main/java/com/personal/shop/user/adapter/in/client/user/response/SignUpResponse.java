package com.personal.shop.user.adapter.in.client.user.response;

import com.personal.shop.user.port.in.result.SignUpResult;

public record SignUpResponse(
        Long id,
        String roleId
) {
    public static SignUpResponse from(SignUpResult signUpResult) {
        return new SignUpResponse(signUpResult.id(), signUpResult.roleId());
    }
}
