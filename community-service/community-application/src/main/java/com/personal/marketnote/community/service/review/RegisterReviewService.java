package com.personal.marketnote.community.service.review;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.review.ProductReviewAggregate;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.domain.review.ReviewCreateState;
import com.personal.marketnote.community.exception.ProductReviewAggregateNotFoundException;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.result.review.RegisterReviewResult;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.RegisterReviewUseCase;
import com.personal.marketnote.community.port.out.review.SaveReviewPort;
import com.personal.marketnote.community.port.out.review.UpdateReviewPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class RegisterReviewService implements RegisterReviewUseCase {
    private final GetReviewUseCase getReviewUseCase;
    private final SaveReviewPort saveReviewPort;
    private final UpdateReviewPort updateReviewPort;

    @Override
    public RegisterReviewResult registerReview(RegisterReviewCommand command) {
        getReviewUseCase.validateDuplicateReview(command);
        Long productId = command.productId();
        Review savedReview = saveReviewPort.save(
                Review.from(
                        ReviewCreateState.builder()
                                .reviewerId(command.reviewerId())
                                .orderId(command.orderId())
                                .productId(productId)
                                .pricePolicyId(command.pricePolicyId())
                                .selectedOptions(command.selectedOptions())
                                .quantity(command.quantity())
                                .reviewerName(command.reviewerName())
                                .rating(command.rating())
                                .content(command.content())
                                .photoYn(command.isPhoto())
                                .build()
                )
        );

        // 상품의 총 리뷰 개수가 100개 미만인 경우 즉시 평점 집계
        if (getReviewUseCase.isReviewCountUnderHundred(productId)) {
            try {
                ProductReviewAggregate productReviewAggregate = getReviewUseCase.getProductReviewAggregate(productId);
                int point = command.rating().intValue();
                ProductReviewAggregate.addPoint(productReviewAggregate, point);
                productReviewAggregate.computeAverageRating(point);
                updateReviewPort.update(productReviewAggregate);
            } catch (ProductReviewAggregateNotFoundException pranfe) {
                saveReviewPort.save(
                        ProductReviewAggregate.from(savedReview)
                );
            }
        }

        return RegisterReviewResult.from(savedReview);
    }
}
