package com.personal.marketnote.user.adapter.in.client.user.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.AcceptOrCancelTermsApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.GetAllTermsApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.mapper.TermsRequestToCommandMapper;
import com.personal.marketnote.user.adapter.in.client.user.request.AcceptOrCancelTermsRequest;
import com.personal.marketnote.user.adapter.in.client.user.response.GetTermsResponse;
import com.personal.marketnote.user.adapter.in.client.user.response.UpdateUserTermsResponse;
import com.personal.marketnote.user.port.in.result.UpdateUserTermsResult;
import com.personal.marketnote.user.port.in.usecase.terms.GetTermsUseCase;
import com.personal.marketnote.user.port.in.usecase.terms.UpdateTermsUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

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
    private final UpdateTermsUseCase updateTermsUseCase;

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
    @AcceptOrCancelTermsApiDocs
    public ResponseEntity<BaseResponse<UpdateUserTermsResponse>> acceptOrCancelTerms(
            @Valid @RequestBody AcceptOrCancelTermsRequest acceptOrCancelTermsRequest,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        UpdateUserTermsResult updateUserTermsResult = updateTermsUseCase.acceptOrCancelTerms(
                ElementExtractor.extractUserId(principal),
                TermsRequestToCommandMapper.mapToCommand(acceptOrCancelTermsRequest)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        UpdateUserTermsResponse.from(updateUserTermsResult),
                        HttpStatus.OK,
                        "약관 동의/철회 성공"
                ),
                HttpStatus.OK
        );
    }
}
