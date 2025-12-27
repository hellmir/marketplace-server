package com.personal.marketnote.user.port.in.result;

public record GetUserResult(
        Long id,
        String roleId,
        String password
) {
    public static GetUserResult of(Long id, String roleId, String password) {
        return new GetUserResult(id, roleId, password);
    }
}
