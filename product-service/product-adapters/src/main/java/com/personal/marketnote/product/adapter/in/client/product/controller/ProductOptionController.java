package com.personal.marketnote.product.adapter.in.client.product.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.AuthorityValidator;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.product.adapter.in.client.product.controller.apidocs.RegisterProductOptionsApiDocs;
import com.personal.marketnote.product.adapter.in.client.product.mapper.ProductRequestToCommandMapper;
import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductOptionsRequest;
import com.personal.marketnote.product.adapter.in.client.product.response.RegisterProductOptionsResponse;
import com.personal.marketnote.product.port.in.result.RegisterProductOptionsResult;
import com.personal.marketnote.product.port.in.usecase.product.RegisterProductOptionsUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_OR_SELLER_POINTCUT;

@RestController
@RequestMapping("/api/v1/products/{productId}")
@Tag(name = "상품 옵션 API", description = "상품 옵션 관련 API")
@RequiredArgsConstructor
@Slf4j
public class ProductOptionController {
    private final RegisterProductOptionsUseCase registerProductOptionsUseCase;

    @PostMapping("/option-categories")
    @PreAuthorize(ADMIN_OR_SELLER_POINTCUT)
    @RegisterProductOptionsApiDocs
    public ResponseEntity<BaseResponse<RegisterProductOptionsResponse>> registerProductOptionCategories(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody RegisterProductOptionsRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        RegisterProductOptionsResult result = registerProductOptionsUseCase.registerProductOptions(
                ElementExtractor.extractUserId(principal),
                AuthorityValidator.hasAdminRole(principal),
                ProductRequestToCommandMapper.mapToCommand(productId, request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterProductOptionsResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "상품 카테고리 및 옵션 목록 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }
}
