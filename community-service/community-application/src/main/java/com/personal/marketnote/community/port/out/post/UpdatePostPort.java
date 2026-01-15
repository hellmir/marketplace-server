package com.personal.marketnote.community.port.out.post;

import com.personal.marketnote.community.domain.post.Post;
import com.personal.marketnote.community.exception.PostNotFoundException;

public interface UpdatePostPort {
    void update(Post post) throws PostNotFoundException;
}
