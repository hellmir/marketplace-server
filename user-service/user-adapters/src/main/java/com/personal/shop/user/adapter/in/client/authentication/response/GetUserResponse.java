package com.personal.shop.user.adapter.in.client.authentication.response;

public record GetUserResponse(
        Long memberId,
        String roleId
) {
}
