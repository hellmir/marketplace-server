package com.personal.marketnote.community.port.in.usecase.board;

import com.personal.marketnote.community.domain.post.Board;
import com.personal.marketnote.community.port.in.result.post.GetPostCategoriesResult;

public interface GetBoardUseCase {
    /**
     * @param board 게시판
     * @return 게시글 카테고리 목록 조회 결과 {@link GetPostCategoriesResult}
     * @Date 2026-01-15
     * @Author 성효빈
     * @Description 게시글 카테고리 목록을 조회합니다.
     */
    GetPostCategoriesResult getCategories(Board board);
}
