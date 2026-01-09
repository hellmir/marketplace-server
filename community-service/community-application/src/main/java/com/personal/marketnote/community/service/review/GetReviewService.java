package com.personal.marketnote.community.service.review;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.exception.ReviewAlreadyExistsException;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.out.review.FindReviewPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetReviewService implements GetReviewUseCase {
    private final FindReviewPort findReviewPort;

    @Override
    public void validateDuplicateReview(RegisterReviewCommand command) {
        Long orderId = command.orderId();
        Long pricePolicyId = command.pricePolicyId();

        if (findReviewPort.existsByOrderIdAndPricePolicyId(orderId, pricePolicyId)) {
            throw new ReviewAlreadyExistsException(orderId, pricePolicyId);
        }
        ;
    }
}
