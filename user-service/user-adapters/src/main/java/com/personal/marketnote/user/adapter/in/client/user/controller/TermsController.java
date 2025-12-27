package com.personal.marketnote.user.adapter.in.client.user.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.AcceptTermsApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.GetAllTermsApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.mapper.TermsRequestToCommandMapper;
import com.personal.marketnote.user.adapter.in.client.user.request.AcceptTermsRequest;
import com.personal.marketnote.user.adapter.in.client.user.response.GetTermsResponse;
import com.personal.marketnote.user.port.in.usecase.terms.AcceptTermsUseCase;
import com.personal.marketnote.user.port.in.usecase.terms.GetTermsUseCase;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/terms")
@Tag(
        name = "회원 약관 API",
        description = "회원 약관 관련 API"
)
@RequiredArgsConstructor
@Slf4j
public class TermsController {
    private final GetTermsUseCase getTermsUseCase;
    private final AcceptTermsUseCase acceptTermsUseCase;

    @GetMapping()
    @GetAllTermsApiDocs
    public ResponseEntity<BaseResponse<GetTermsResponse>> getAllTerms() {
        GetTermsResponse getTermsResponse = GetTermsResponse.from(
                getTermsUseCase.getAllTerms()
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        getTermsResponse,
                        HttpStatus.OK,
                        "전체 약관 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping()
    @AcceptTermsApiDocs
    public ResponseEntity<BaseResponse<Void>> acceptTerms(
            @Valid @RequestBody AcceptTermsRequest acceptTermsRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        acceptTermsUseCase.acceptTerms(
                ElementExtractor.extractUserId(principal),
                TermsRequestToCommandMapper.mapToCommand(acceptTermsRequest)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        "약관 동의 성공"
                ),
                HttpStatus.OK
        );
    }
}
