package com.personal.marketnote.community.service.board;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.post.*;
import com.personal.marketnote.community.port.in.result.post.GetPostCategoriesResult;
import com.personal.marketnote.community.port.in.usecase.board.GetBoardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetBoardService implements GetBoardUseCase {
    @Override
    public GetPostCategoriesResult getCategories(Board board) {
        if (board.isNotice()) {
            return GetPostCategoriesResult.from(NoticePostCategory.values());
        }

        if (board.isFaq()) {
            return GetPostCategoriesResult.from(FaqPostCategory.values());
        }

        if (board.isProductInquery()) {
            return GetPostCategoriesResult.from(ProductInqueryPostCategory.values());
        }

        if (board.isOneOnOneInquery()) {
            return GetPostCategoriesResult.from(OneOnOneInqueryPostCategory.values());
        }

        throw new IllegalArgumentException("유효하지 않은 게시판입니다. 전송된 게시판: " + board);
    }
}
