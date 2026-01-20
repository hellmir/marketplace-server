package com.personal.marketnote.community.adapter.in.web.post.response;

import com.personal.marketnote.community.port.in.result.post.RegisterPostResult;

public record RegisterPostResponse(
        Long id
) {
    public static RegisterPostResponse from(RegisterPostResult result) {
        return new RegisterPostResponse(result.id());
    }
}
