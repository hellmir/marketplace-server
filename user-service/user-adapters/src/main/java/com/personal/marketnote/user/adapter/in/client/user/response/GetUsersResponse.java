package com.personal.marketnote.user.adapter.in.client.user.response;

import com.personal.marketnote.user.port.in.result.GetUserResult;

import java.util.List;
import java.util.stream.Collectors;

public record GetUsersResponse(
        List<GetUserResponse> users
) {
    public static GetUsersResponse from(List<GetUserResult> getUserResults) {
        return new GetUsersResponse(getUserResults.stream()
                .map(GetUserResponse::from)
                .collect(Collectors.toList()));
    }
}
