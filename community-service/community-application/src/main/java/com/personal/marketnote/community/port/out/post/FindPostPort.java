package com.personal.marketnote.community.port.out.post;

import com.personal.marketnote.community.domain.post.Board;
import com.personal.marketnote.community.domain.post.PostSortProperty;
import com.personal.marketnote.community.domain.post.PostTargetType;
import com.personal.marketnote.community.domain.post.Posts;
import org.springframework.data.domain.Pageable;

public interface FindPostPort {
    /**
     * @param id 게시글 ID
     * @return 게시글 존재 여부 {@link boolean}
     * @Date 2026-01-13
     * @Author 성효빈
     * @Description 게시글 존재 여부를 조회합니다.
     */
    boolean existsById(Long id);

    /**
     * @param board      게시판
     * @param category   카테고리
     * @param targetType 대상 유형
     * @param targetId   대상 ID
     * @param cursor     커서(무한 스크롤 페이지 설정)
     * @param pageable   페이지네이션 정보
     * @return 게시글 목록 {@link Posts}
     * @Date 2025-12-06
     * @Author 성효빈
     * @Description 게시글 목록을 조회합니다.
     */
    Posts findPosts(
            Board board,
            String category,
            PostTargetType targetType,
            Long targetId,
            Long cursor,
            Pageable pageable,
            boolean isDesc,
            PostSortProperty sortProperty
    );

    /**
     * @param userId   사용자 ID
     * @param board    게시판
     * @param cursor   커서(무한 스크롤 페이지 설정)
     * @param pageable 페이지네이션 정보
     * @return 게시글 목록 {@link Posts}
     * @Date 2025-12-06
     * @Author 성효빈
     * @Description 회원 게시글 목록을 조회합니다.
     */
    Posts findUserPosts(
            Long userId,
            Board board,
            Long cursor,
            Pageable pageable,
            boolean isDesc,
            PostSortProperty sortProperty
    );

    /**
     * @param board      게시판
     * @param category   카테고리
     * @param targetType 대상 유형
     * @param targetId   대상 ID
     * @return 게시글 개수 {@link long}
     * @Date 2025-12-06
     * @Author 성효빈
     * @Description 게시글 개수를 조회합니다.
     */
    long count(Board board, String category, PostTargetType targetType, Long targetId);

    /**
     * @param userId 사용자 ID
     * @param board  게시판
     * @return 게시글 개수 {@link long}
     * @Date 2025-12-06
     * @Author 성효빈
     * @Description 내 게시글 개수를 조회합니다.
     */
    long count(Long userId, Board board);
}
