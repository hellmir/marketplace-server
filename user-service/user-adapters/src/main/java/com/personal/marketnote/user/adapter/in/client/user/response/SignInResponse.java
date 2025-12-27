package com.personal.marketnote.user.adapter.in.client.user.response;

import com.personal.marketnote.user.port.in.result.SignInResult;

public record SignInResponse(
        Long id,
        String roleId
) {
    public static SignInResponse from(SignInResult signInResult) {
        return new SignInResponse(signInResult.id(), signInResult.roleId());
    }
}
