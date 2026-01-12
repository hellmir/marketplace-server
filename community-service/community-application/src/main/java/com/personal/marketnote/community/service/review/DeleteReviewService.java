package com.personal.marketnote.community.service.review;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.port.in.usecase.review.DeleteReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.out.review.UpdateReviewPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class DeleteReviewService implements DeleteReviewUseCase {
    private final GetReviewUseCase getReviewUseCase;
    private final UpdateReviewPort updateReviewPort;

    @Override
    public void deleteReview(Long id, Long reviewerId) {
        getReviewUseCase.validateAuthor(id, reviewerId);
        Review review = getReviewUseCase.getReview(id);
        review.delete();
        updateReviewPort.update(review);
    }
}
