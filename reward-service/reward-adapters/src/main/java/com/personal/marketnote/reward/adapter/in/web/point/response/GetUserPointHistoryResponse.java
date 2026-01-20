package com.personal.marketnote.reward.adapter.in.web.point.response;

import com.personal.marketnote.reward.port.in.result.point.GetUserPointHistoryResult;

import java.util.List;

public record GetUserPointHistoryResponse(
        List<UserPointHistoryByDateResponse> histories
) {
    public static GetUserPointHistoryResponse from(GetUserPointHistoryResult result) {
        return new GetUserPointHistoryResponse(
                result.histories().stream()
                        .map(UserPointHistoryByDateResponse::from)
                        .toList()
        );
    }
}

