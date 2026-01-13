package com.personal.marketnote.community.port.out.post;

import com.personal.marketnote.community.domain.post.Post;

public interface SavePostPort {
    Post save(Post post);
}
