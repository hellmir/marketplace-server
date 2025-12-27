package com.personal.marketnote.user.adapter.in.client.user.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.user.adapter.in.client.user.controller.apidocs.GetAllTermsApiDocs;
import com.personal.marketnote.user.adapter.in.client.user.response.GetTermsResponse;
import com.personal.marketnote.user.port.in.usecase.GetUserTermsUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final GetUserTermsUseCase getUserTermsUseCase;

    @GetMapping()
    @GetAllTermsApiDocs
    public ResponseEntity<BaseResponse<GetTermsResponse>> getAllTerms() {
        GetTermsResponse getTermsResponse = GetTermsResponse.from(
                getUserTermsUseCase.getAllTerms()
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
}
