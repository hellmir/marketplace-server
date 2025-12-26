package com.personal.shop.user.port.in.result;

public record GetUserResult(
        Long id,
        String roleId
) {
    public static GetUserResult of(Long id, String roleId) {
        return new GetUserResult(id, roleId);
    }
}
