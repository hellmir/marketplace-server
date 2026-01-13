package com.personal.marketnote.community.port.in.usecase.post;

import com.personal.marketnote.community.port.in.command.post.RegisterPostCommand;
import com.personal.marketnote.community.port.in.result.post.RegisterPostResult;

public interface RegisterPostUseCase {
    /**
     * @param command 게시글 등록 커맨드
     * @return 게시글 등록 결과 {@link RegisterPostResult}
     */
    RegisterPostResult registerPost(RegisterPostCommand command);
}
