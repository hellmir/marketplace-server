package com.personal.marketnote.community.service.review;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.review.ProductReviewAggregate;
import com.personal.marketnote.community.domain.review.Review;
import com.personal.marketnote.community.exception.ProductReviewAggregateNotFoundException;
import com.personal.marketnote.community.mapper.ReviewCommandToStateMapper;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.result.review.RegisterReviewResult;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.RegisterReviewUseCase;
import com.personal.marketnote.community.port.out.order.UpdateOrderProductReviewStatusPort;
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
    private final UpdateOrderProductReviewStatusPort updateOrderProductReviewStatusPort;

    @Override
    public RegisterReviewResult registerReview(RegisterReviewCommand command) {
        getReviewUseCase.validateDuplicateReview(command);
        Long orderId = command.orderId();
        Long productId = command.productId();
        Long pricePolicyId = command.pricePolicyId();
        Review savedReview = saveReviewPort.save(
                Review.from(ReviewCommandToStateMapper.mapToState(command))
        );

        // 상품 평점 집계
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

        // 주문 상품의 리뷰 작성 여부를 true로 업데이트
        // FIXME: Kafka 이벤트 Production으로 변경
        updateOrderProductReviewStatusPort.update(orderId, pricePolicyId, true);

        return RegisterReviewResult.from(savedReview);
    }
}
