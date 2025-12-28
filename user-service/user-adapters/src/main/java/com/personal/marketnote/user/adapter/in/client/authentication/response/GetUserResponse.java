package com.personal.marketnote.user.adapter.in.client.authentication.response;

import com.personal.marketnote.user.port.in.result.GetUserResult;

public record GetUserResponse(
        GetUserResult userInfo
) {
    public static GetUserResponse from(GetUserResult getUserResult) {
        return new GetUserResponse(getUserResult);
    }
}
