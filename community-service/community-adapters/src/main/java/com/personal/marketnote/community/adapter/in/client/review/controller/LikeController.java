package com.personal.marketnote.community.adapter.in.client.review.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.community.adapter.in.client.review.controller.apidocs.RegisterLikeApiDocs;
import com.personal.marketnote.community.adapter.in.client.review.mapper.LikeRequestToCommandMapper;
import com.personal.marketnote.community.adapter.in.client.review.request.RegisterLikeRequest;
import com.personal.marketnote.community.adapter.in.client.review.response.RegisterLikeResponse;
import com.personal.marketnote.community.port.in.result.like.RegisterLikeResult;
import com.personal.marketnote.community.port.in.usecase.like.RegisterLikeUseCase;
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
@RequestMapping("/api/v1/likes")
@Tag(name = "좋아요 API", description = "좋아요 관련 API")
@RequiredArgsConstructor
public class LikeController {
    private final RegisterLikeUseCase registerLikeUseCase;

    /**
     * 좋아요 등록
     *
     * @param request   좋아요 등록 요청
     * @param principal 인증된 사용자 정보
     * @return 좋아요 등록 응답 {@link RegisterLikeResponse}
     * @Author 성효빈
     * @Date 2026-01-10
     * @Description 대상 도메인에 좋아요를 등록합니다.
     */
    @PostMapping
    @RegisterLikeApiDocs
    public ResponseEntity<BaseResponse<RegisterLikeResponse>> registerLike(
            @Valid @RequestBody RegisterLikeRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        RegisterLikeResult result = registerLikeUseCase.registerLike(
                LikeRequestToCommandMapper.mapToCommand(request, ElementExtractor.extractUserId(principal))
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterLikeResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "좋아요 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }
}
