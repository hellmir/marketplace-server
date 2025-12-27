package com.personal.marketnote.user.adapter.in.client.user.response;

import com.personal.marketnote.user.port.in.result.GetTermResult;

public record GetTermResponse(
        Long id,
        String content
) {
    public static GetTermResponse from(GetTermResult getTermResult) {
        return new GetTermResponse(getTermResult.id(), getTermResult.content());
    }
}
