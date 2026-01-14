package com.personal.marketnote.community.adapter.in.client.review.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.community.adapter.in.client.review.controller.apidocs.*;
import com.personal.marketnote.community.adapter.in.client.review.mapper.ReviewRequestToCommandMapper;
import com.personal.marketnote.community.adapter.in.client.review.request.RegisterReviewRequest;
import com.personal.marketnote.community.adapter.in.client.review.request.UpdateReviewRequest;
import com.personal.marketnote.community.adapter.in.client.review.response.GetProductReviewAggregateResponse;
import com.personal.marketnote.community.adapter.in.client.review.response.GetReviewsResponse;
import com.personal.marketnote.community.adapter.in.client.review.response.RegisterReviewResponse;
import com.personal.marketnote.community.domain.review.ReviewSortProperty;
import com.personal.marketnote.community.port.in.result.review.GetReviewsResult;
import com.personal.marketnote.community.port.in.result.review.ProductReviewAggregateResult;
import com.personal.marketnote.community.port.in.result.review.RegisterReviewResult;
import com.personal.marketnote.community.port.in.usecase.review.DeleteReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.RegisterReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.UpdateReviewUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static org.apache.commons.lang3.BooleanUtils.FALSE;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "리뷰 API", description = "리뷰 관련 API")
@RequiredArgsConstructor
public class ReviewController {
    private static final String GET_PRODUCT_REVIEWS_DEFAULT_PAGE_SIZE = "4";

    private final RegisterReviewUseCase registerReviewUseCase;
    private final GetReviewUseCase getReviewUseCase;
    private final UpdateReviewUseCase updateReviewUseCase;
    private final DeleteReviewUseCase deleteReviewUseCase;

    /**
     * 리뷰 등록
     *
     * @param request   리뷰 등록 요청
     * @param principal 인증된 사용자 정보
     * @return 리뷰 등록 응답 {@link RegisterReviewResponse}
     * @Author 성효빈
     * @Date 2026-01-09
     * @Description 상품 리뷰를 등록합니다.
     */
    @PostMapping("/reviews")
    @RegisterReviewApiDocs
    public ResponseEntity<BaseResponse<RegisterReviewResponse>> registerReview(
            @Valid @RequestBody RegisterReviewRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        RegisterReviewResult result = registerReviewUseCase.registerReview(
                ReviewRequestToCommandMapper.mapToCommand(request, ElementExtractor.extractUserId(principal))
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterReviewResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "리뷰 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }

    /**
     * 상품 리뷰 목록 조회
     *
     * @param productId     상품 ID
     * @param cursor        커서(무한 스크롤 페이지 설정)
     * @param pageSize      페이지 크기
     * @param sortDirection 정렬 방향
     * @param sortProperty  정렬 속성
     * @return 리뷰 목록 조회 응답 {@link GetReviewsResult}
     * @Author 성효빈
     * @Date 2026-01-10
     * @Description 상품 리뷰 목록을 조회합니다.
     */
    @GetMapping("/products/{productId}/reviews")
    @GetProductReviewsApiDocs
    public ResponseEntity<BaseResponse<GetReviewsResponse>> getProductReviews(
            @PathVariable("productId") Long productId,
            @RequestParam(value = "isPhoto", required = false, defaultValue = FALSE) Boolean isPhoto,
            @RequestParam(value = "cursor", required = false) Long cursor,
            @RequestParam(value = "pageSize", required = false, defaultValue = GET_PRODUCT_REVIEWS_DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam(required = false, defaultValue = "ID") ReviewSortProperty sortProperty,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        Long userId = null;
        if (FormatValidator.hasValue(principal)) {
            userId = ElementExtractor.extractUserId(principal);
        }

        GetReviewsResult result = getReviewUseCase.getProductReviews(
                userId,
                productId,
                isPhoto,
                cursor,
                pageSize,
                sortDirection,
                sortProperty
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetReviewsResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "상품 리뷰 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 나의 리뷰 목록 조회
     *
     * @param cursor        커서(무한 스크롤 페이지 설정)
     * @param pageSize      페이지 크기
     * @param sortDirection 정렬 방향
     * @param sortProperty  정렬 속성
     * @return 리뷰 목록 조회 응답 {@link GetReviewsResult}
     * @Author 성효빈
     * @Date 2026-01-12
     * @Description 나의 리뷰 목록을 조회합니다.
     */
    @GetMapping("/reviews")
    @GetMyReviewsApiDocs
    public ResponseEntity<BaseResponse<GetReviewsResponse>> getMyReviews(
            @RequestParam(value = "cursor", required = false) Long cursor,
            @RequestParam(value = "pageSize", required = false, defaultValue = GET_PRODUCT_REVIEWS_DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam(required = false, defaultValue = "ID") ReviewSortProperty sortProperty,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        GetReviewsResult result = getReviewUseCase.getMyReviews(
                ElementExtractor.extractUserId(principal),
                cursor,
                pageSize,
                sortDirection,
                sortProperty
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetReviewsResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "나의 리뷰 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 리뷰 수정
     *
     * @param id        리뷰 ID
     * @param request   리뷰 수정 요청
     * @param principal 인증된 사용자 정보
     * @Author 성효빈
     * @Date 2026-01-12
     * @Description 상품 리뷰를 수정합니다.
     */
    @PatchMapping("/reviews/{id}")
    @UpdateReviewApiDocs
    public ResponseEntity<BaseResponse<Void>> updateReview(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateReviewRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        updateReviewUseCase.updateReview(
                ReviewRequestToCommandMapper.mapToCommand(id, request, ElementExtractor.extractUserId(principal))
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "리뷰 수정 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 리뷰 삭제
     *
     * @param id        리뷰 ID
     * @param principal 인증된 사용자 정보
     * @Author 성효빈
     * @Date 2026-01-12
     * @Description 상품 리뷰를 삭제합니다.
     */
    @DeleteMapping("/reviews/{id}")
    @DeleteReviewApiDocs
    public ResponseEntity<BaseResponse<Void>> deleteReview(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        deleteReviewUseCase.deleteReview(id, ElementExtractor.extractUserId(principal));

        return new ResponseEntity<>(
                BaseResponse.of(
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "리뷰 삭제 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 상품 리뷰 평점 평균 및 점수별 개수 현황 조회
     *
     * @param productId 상품 ID
     * @return 상품 리뷰 평점 평균 및 점수별 개수 현황 조회 응답 {@link GetProductReviewAggregateResponse}
     * @Author 성효빈
     * @Date 2026-01-10
     * @Description 상품 리뷰 평점 평균 및 점수별 개수 현황을 조회합니다.
     */
    @GetMapping("products/{productId}/review-aggregate")
    @GetProductReviewAggregateApiDocs
    public ResponseEntity<BaseResponse<GetProductReviewAggregateResponse>> getProductReviewAggregate(
            @PathVariable("productId") Long productId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        ProductReviewAggregateResult result = ProductReviewAggregateResult.from(
                getReviewUseCase.getProductReviewAggregate(productId)
        );

        return new ResponseEntity<>(
                BaseResponse.of(GetProductReviewAggregateResponse.from(result), HttpStatus.OK, DEFAULT_SUCCESS_CODE, "상품 리뷰 평점 평균 및 점수별 개수 현황 조회 성공"),
                HttpStatus.OK
        );
    }
}
