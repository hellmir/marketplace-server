package com.personal.marketnote.community.adapter.in.client.post.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.community.adapter.in.client.post.controller.apidocs.RegisterPostApiDocs;
import com.personal.marketnote.community.adapter.in.client.post.mapper.PostRequestToCommandMapper;
import com.personal.marketnote.community.adapter.in.client.post.request.RegisterPostRequest;
import com.personal.marketnote.community.adapter.in.client.post.response.RegisterPostResponse;
import com.personal.marketnote.community.port.in.result.post.RegisterPostResult;
import com.personal.marketnote.community.port.in.usecase.post.RegisterPostUseCase;
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
@RequestMapping("/api/v1")
@Tag(name = "게시글 API", description = "게시글 관련 API")
@RequiredArgsConstructor
public class PostController {
    private final RegisterPostUseCase registerPostUseCase;

    /**
     * 게시글 등록
     *
     * @param request   게시글 등록 요청
     * @param principal 인증된 사용자 정보
     * @return 게시글 등록 응답 {@link RegisterPostResponse}
     * @Author 성효빈
     * @Date 2026-01-13
     * @Description 게시글을 등록합니다.
     */
    @PostMapping("/posts")
    @RegisterPostApiDocs
    public ResponseEntity<BaseResponse<RegisterPostResponse>> registerPost(
            @Valid @RequestBody RegisterPostRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        RegisterPostResult result = registerPostUseCase.registerPost(
                PostRequestToCommandMapper.mapToCommand(request, ElementExtractor.extractUserId(principal))
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterPostResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "게시글 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }
}
