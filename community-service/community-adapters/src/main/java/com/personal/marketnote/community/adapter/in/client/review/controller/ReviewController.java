package com.personal.marketnote.community.adapter.in.client.review.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.community.adapter.in.client.review.controller.apidocs.RegisterReviewApiDocs;
import com.personal.marketnote.community.adapter.in.client.review.mapper.ReviewRequestToCommandMapper;
import com.personal.marketnote.community.adapter.in.client.review.request.RegisterReviewRequest;
import com.personal.marketnote.community.adapter.in.client.review.response.RegisterReviewResponse;
import com.personal.marketnote.community.port.in.result.review.RegisterReviewResult;
import com.personal.marketnote.community.port.in.usecase.review.RegisterReviewUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "리뷰 API", description = "리뷰 관련 API")
@RequiredArgsConstructor
public class ReviewController {
    private final RegisterReviewUseCase registerReviewUseCase;

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
    @PostMapping
    @RegisterReviewApiDocs
    public ResponseEntity<BaseResponse<RegisterReviewResponse>> registerReview(
            @Valid @RequestBody RegisterReviewRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        RegisterReviewResult result = registerReviewUseCase.registerReview(
                ReviewRequestToCommandMapper.mapToCommand(
                        request,
                        ElementExtractor.extractUserId(principal)
                )
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
}
