package com.personal.marketnote.product.adapter.in.client.product.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.AuthorityValidator;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.product.adapter.in.client.product.controller.apidocs.RegisterPricePolicyApiDocs;
import com.personal.marketnote.product.adapter.in.client.product.request.RegisterPricePolicyRequest;
import com.personal.marketnote.product.adapter.in.client.product.response.RegisterPricePolicyResponse;
import com.personal.marketnote.product.port.in.command.RegisterPricePolicyCommand;
import com.personal.marketnote.product.port.in.result.RegisterPricePolicyResult;
import com.personal.marketnote.product.port.in.usecase.product.RegisterPricePolicyUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_OR_SELLER_POINTCUT;

@RestController
@RequestMapping("/api/v1/products/{productId}/price-policies")
@Tag(name = "상품 가격 정책 API", description = "상품 가격 정책 관련 API")
@RequiredArgsConstructor
public class PricePolicyController {
    private final RegisterPricePolicyUseCase registerPricePolicyUseCase;

    @PostMapping
    @PreAuthorize(ADMIN_OR_SELLER_POINTCUT)
    @RegisterPricePolicyApiDocs
    public ResponseEntity<BaseResponse<RegisterPricePolicyResponse>> registerPricePolicy(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody RegisterPricePolicyRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        RegisterPricePolicyResult result = registerPricePolicyUseCase.registerPricePolicy(
                ElementExtractor.extractUserId(principal),
                AuthorityValidator.hasAdminRole(principal),
                RegisterPricePolicyCommand.of(
                        productId,
                        request.getPrice(),
                        request.getCurrentPrice(),
                        request.getAccumulatedPoint()
                )
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterPricePolicyResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "상품 가격 정책 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }
}


