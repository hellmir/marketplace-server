package com.personal.marketnote.community.port.in.usecase.post;

public interface GetPostUseCase {
    /**
     * @param id 게시글 ID
     * @return 게시글 존재 여부 {@link boolean}
     * @Date 2026-01-13
     * @Author 성효빈
     * @Description 게시글 존재 여부를 조회합니다.
     */
    boolean existsPost(Long id);
}
