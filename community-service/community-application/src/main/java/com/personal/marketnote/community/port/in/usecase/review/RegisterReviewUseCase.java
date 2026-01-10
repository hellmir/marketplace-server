package com.personal.marketnote.community.port.in.usecase.review;

import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.result.review.RegisterReviewResult;

public interface RegisterReviewUseCase {
    /**
     * @param command 리뷰 등록 커맨드
     * @return 리뷰 등록 결과 {@link RegisterReviewResult}
     * @Date 2026-01-09
     * @Author 성효빈
     * @Description 리뷰를 등록합니다.
     */
    RegisterReviewResult registerReview(RegisterReviewCommand command);
}
