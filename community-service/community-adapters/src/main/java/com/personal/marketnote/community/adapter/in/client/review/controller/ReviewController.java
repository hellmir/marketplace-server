package com.personal.marketnote.community.adapter.in.client.review.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.community.adapter.in.client.review.controller.apidocs.GetProductReviewsApiDocs;
import com.personal.marketnote.community.adapter.in.client.review.controller.apidocs.RegisterReviewApiDocs;
import com.personal.marketnote.community.adapter.in.client.review.mapper.ReviewRequestToCommandMapper;
import com.personal.marketnote.community.adapter.in.client.review.request.RegisterReviewRequest;
import com.personal.marketnote.community.adapter.in.client.review.response.GetReviewsResponse;
import com.personal.marketnote.community.adapter.in.client.review.response.RegisterReviewResponse;
import com.personal.marketnote.community.domain.review.ReviewSortProperty;
import com.personal.marketnote.community.port.in.result.review.GetReviewsResult;
import com.personal.marketnote.community.port.in.result.review.RegisterReviewResult;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.RegisterReviewUseCase;
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
    @GetMapping("products/{productId}/reviews")
    @GetProductReviewsApiDocs
    public ResponseEntity<BaseResponse<GetReviewsResponse>> getProductReviews(
            @PathVariable("productId") Long productId,
            @RequestParam(value = "isPhoto", required = false, defaultValue = FALSE) Boolean isPhoto,
            @RequestParam(value = "cursor", required = false) Long cursor,
            @RequestParam(value = "pageSize", required = false, defaultValue = GET_PRODUCT_REVIEWS_DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam(required = false, defaultValue = "ID") ReviewSortProperty sortProperty
    ) {
        GetReviewsResult result = getReviewUseCase.getProductReviews(
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
}
