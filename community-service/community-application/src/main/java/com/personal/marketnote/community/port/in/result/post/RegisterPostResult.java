package com.personal.marketnote.community.port.in.result.post;

import com.personal.marketnote.community.domain.post.Post;

public record RegisterPostResult(
        Long id
) {
    public static RegisterPostResult from(Post post) {
        return new RegisterPostResult(post.getId());
    }
}
