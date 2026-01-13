package com.personal.marketnote.community.service.review;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.domain.review.ProductReviewAggregate;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.domain.review.ReviewVersionHistory;
import com.personal.marketnote.community.domain.review.ReviewVersionHistoryCreateState;
import com.personal.marketnote.community.mapper.ReviewCommandToStateMapper;
import com.personal.marketnote.community.port.in.command.review.UpdateReviewCommand;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.UpdateReviewUseCase;
import com.personal.marketnote.community.port.out.review.SaveReviewPort;
import com.personal.marketnote.community.port.out.review.UpdateReviewPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class UpdateReviewService implements UpdateReviewUseCase {
    private final GetReviewUseCase getReviewUseCase;
    private final SaveReviewPort saveReviewPort;
    private final UpdateReviewPort updateReviewPort;

    @Override
    public void updateReview(UpdateReviewCommand command) {
        Long id = command.id();
        getReviewUseCase.validateAuthor(id, command.reviewerId());
        Review review = getReviewUseCase.getReview(id);
        Float previousRating = review.getRating();
        Float newRating = command.rating();
        review.update(newRating, command.content(), command.isPhoto());
        updateReviewPort.update(review);

        // 리뷰 버전 기록 저장
        ReviewVersionHistoryCreateState reviewVersionHistoryCreateState
                = ReviewCommandToStateMapper.mapToVersionHistoryState(review.getId(), command);
        saveReviewPort.saveVersionHistory(
                ReviewVersionHistory.from(reviewVersionHistoryCreateState)
        );

        // 상품 평점 재집계
        if (!FormatValidator.equals(previousRating, newRating)) {
            ProductReviewAggregate productReviewAggregate = getReviewUseCase.getProductReviewAggregate(review.getProductId());
            ProductReviewAggregate.changePoint(productReviewAggregate, previousRating, newRating);
            productReviewAggregate.computeAverageRating(previousRating.intValue(), newRating.intValue());
            updateReviewPort.update(productReviewAggregate);
        }
    }
}
