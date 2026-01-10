package com.personal.marketnote.community.service.review;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.result.review.RegisterReviewResult;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.RegisterReviewUseCase;
import com.personal.marketnote.community.port.out.review.SaveReviewPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class RegisterReviewService implements RegisterReviewUseCase {
    private final SaveReviewPort saveReviewPort;
    private final GetReviewUseCase getReviewUseCase;

    @Override
    public RegisterReviewResult registerReview(RegisterReviewCommand command) {
        getReviewUseCase.validateDuplicateReview(command);
        Review savedReview = saveReviewPort.save(
                Review.of(
                        command.reviewerId(),
                        command.orderId(),
                        command.productId(),
                        command.pricePolicyId(),
                        command.selectedOptions(),
                        command.quantity(),
                        command.reviewerName(),
                        roundScore(command.score()),
                        command.content(),
                        command.isPhoto()
                )
        );

        return RegisterReviewResult.from(savedReview);
    }

    private Float roundScore(Float score) {
        return BigDecimal.valueOf(score)
                .setScale(0, RoundingMode.HALF_UP)
                .floatValue();
    }
}
