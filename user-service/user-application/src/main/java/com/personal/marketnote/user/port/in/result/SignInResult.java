package com.personal.marketnote.user.port.in.result;

public record SignInResult(
        Long id,
        String roleId
) {
    public static SignInResult from(GetUserResult getUserResult) {
        return new SignInResult(getUserResult.id(), getUserResult.roleId());
    }
}
