package com.personal.marketnote.community.service.review;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.domain.review.*;
import com.personal.marketnote.community.exception.*;
import com.personal.marketnote.community.port.in.command.review.RegisterReviewCommand;
import com.personal.marketnote.community.port.in.result.review.GetReviewsResult;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.out.review.FindReviewPort;
import com.personal.marketnote.community.port.out.review.FindReviewReportPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetReviewService implements GetReviewUseCase {
    private final FindReviewPort findReviewPort;
    private final FindReviewReportPort findReviewReportPort;

    @Override
    public Review getReview(Long id) {
        return findReviewPort.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @Override
    public void validateDuplicateReview(RegisterReviewCommand command) {
        Long orderId = command.orderId();
        Long pricePolicyId = command.pricePolicyId();

        if (findReviewPort.existsByOrderIdAndPricePolicyId(orderId, pricePolicyId)) {
            throw new ReviewAlreadyExistsException(orderId, pricePolicyId);
        }
    }

    @Override
    public GetReviewsResult getProductReviews(
            Long userId,
            Long productId,
            Boolean isPhoto,
            Long cursor,
            int pageSize,
            Sort.Direction sortDirection,
            ReviewSortProperty sortProperty
    ) {
        Pageable pageable = PageRequest.of(
                0, pageSize + 1, Sort.by(sortDirection, sortProperty.getCamelCaseValue())
        );

        Reviews productReviews = findReviewPort.findProductReviews(
                productId, isPhoto, cursor, pageable, sortProperty
        );

        // 무한 스크롤 페이지 설정
        boolean hasNext = productReviews.size() > pageSize;
        List<Review> pagedReviews = hasNext
                ? productReviews.subList(0, pageSize)
                : productReviews.getReviews();

        Long nextCursor = null;
        if (FormatValidator.hasValue(pagedReviews)) {
            nextCursor = pagedReviews.getLast().getId();
        }

        boolean isFirstPage = !FormatValidator.hasValue(cursor);
        Long totalElements = null;
        if (isFirstPage) {
            totalElements = findReviewPort.countActive(productId, isPhoto);
        }

        if (!isPhoto && FormatValidator.hasValue(userId)) {
            // 로그인 사용자가 각 리뷰에 좋아요를 눌렀는지 여부 업데이트
            pagedReviews.forEach(review -> review.updateIsUserLiked(userId));
        }

        return GetReviewsResult.from(hasNext, nextCursor, totalElements, pagedReviews);
    }

    @Override
    public boolean isReviewCountUnderHundred(Long productId) {
        return findReviewPort.countActive(productId, false) < 100;
    }

    @Override
    @Transactional(isolation = READ_COMMITTED, readOnly = true, propagation = REQUIRES_NEW)
    public ProductReviewAggregate getProductReviewAggregate(Long productId) {
        return findReviewPort.findProductReviewAggregateByProductId(productId)
                .orElseThrow(() -> new ProductReviewAggregateNotFoundException(productId));
    }

    @Override
    public GetReviewsResult getMyReviews(
            Long userId, Long cursor, int pageSize, Sort.Direction sortDirection, ReviewSortProperty sortProperty
    ) {
        Pageable pageable = PageRequest.of(
                0, pageSize + 1, Sort.by(sortDirection, sortProperty.getCamelCaseValue())
        );

        Reviews userReviews = findReviewPort.findUserReviews(
                userId, cursor, pageable, sortProperty
        );

        // 무한 스크롤 페이지 설정
        boolean hasNext = userReviews.size() > pageSize;
        List<Review> pagedReviews = hasNext
                ? userReviews.subList(0, pageSize)
                : userReviews.getReviews();

        Long nextCursor = null;
        if (FormatValidator.hasValue(pagedReviews)) {
            nextCursor = pagedReviews.getLast().getId();
        }

        boolean isFirstPage = !FormatValidator.hasValue(cursor);
        Long totalElements = null;
        if (isFirstPage) {
            totalElements = findReviewPort.countActive(userId);
        }

        return GetReviewsResult.from(hasNext, nextCursor, totalElements, pagedReviews);
    }

    @Override
    public void validateDuplicateReport(Long id, Long reporterId) {
        if (findReviewReportPort.existsByReviewIdAndReporterId(id, reporterId)) {
            throw new ReviewAlreadyReportedException(id, reporterId);
        }
    }

    @Override
    public List<ReviewReport> getReviewReports(Long id) {
        return findReviewReportPort.findByReviewId(id);
    }

    @Override
    public void validateAuthor(Long id, Long reviewerId) {
        if (!findReviewPort.existsByIdAndReviewerId(id, reviewerId)) {
            throw new NotReviewAuthorException(id, reviewerId);
        }
    }
}
