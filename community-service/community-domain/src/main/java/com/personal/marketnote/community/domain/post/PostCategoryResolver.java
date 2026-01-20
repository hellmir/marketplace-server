package com.personal.marketnote.community.domain.post;

import com.personal.marketnote.common.utility.FormatValidator;

public class PostCategoryResolver {
    public static PostCategory resolve(Board board, String categoryCode) {
        if (FormatValidator.hasNoValue(board) || FormatValidator.hasNoValue(categoryCode)) {
            throw new IllegalArgumentException("게시판 또는 카테고리가 없습니다.");
        }

        return board.resolveCategory(categoryCode);
    }
}
