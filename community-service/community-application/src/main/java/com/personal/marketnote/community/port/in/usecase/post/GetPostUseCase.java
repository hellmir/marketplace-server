package com.personal.marketnote.community.port.in.usecase.post;

import com.personal.marketnote.community.domain.post.Post;
import com.personal.marketnote.community.port.in.command.post.GetPostsCommand;
import com.personal.marketnote.community.port.in.result.post.GetPostsResult;

public interface GetPostUseCase {
    /**
     * @param id 게시글 ID
     * @return 게시글 존재 여부 {@link boolean}
     * @Date 2026-01-13
     * @Author 성효빈
     * @Description 게시글 존재 여부를 조회합니다.
     */
    boolean existsPost(Long id);

    /**
     * @param query 게시글 조회 조건
     * @return 게시글 목록 조회 결과 {@link GetPostsResult}
     * @Date 2025-12-06
     * @Author 성효빈
     * @Description 게시글 목록을 조회합니다.
     */
    GetPostsResult getPosts(GetPostsCommand query);

    /**
     * @param id 게시글 ID
     * @return 게시글 {@link Post}
     * @Date 2026-01-15
     * @Author 성효빈
     * @Description 게시글을 조회합니다.
     */
    Post getPost(Long id);
}
