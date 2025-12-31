package com.personal.marketnote.product.adapter.in.client.product.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.AuthorityValidator;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.product.adapter.in.client.product.controller.apidocs.GetProductsApiDocs;
import com.personal.marketnote.product.adapter.in.client.product.controller.apidocs.RegisterProductApiDocs;
import com.personal.marketnote.product.adapter.in.client.product.controller.apidocs.RegisterProductCategoriesApiDocs;
import com.personal.marketnote.product.adapter.in.client.product.mapper.ProductRequestToCommandMapper;
import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductCategoriesRequest;
import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductRequest;
import com.personal.marketnote.product.adapter.in.client.product.response.GetProductsResponse;
import com.personal.marketnote.product.adapter.in.client.product.response.RegisterProductCategoriesResponse;
import com.personal.marketnote.product.adapter.in.client.product.response.RegisterProductResponse;
import com.personal.marketnote.product.port.in.result.GetProductsResult;
import com.personal.marketnote.product.port.in.result.RegisterProductCategoriesResult;
import com.personal.marketnote.product.port.in.result.RegisterProductResult;
import com.personal.marketnote.product.port.in.usecase.product.GetProductUseCase;
import com.personal.marketnote.product.port.in.usecase.product.RegisterProductCategoriesUseCase;
import com.personal.marketnote.product.port.in.usecase.product.RegisterProductUseCase;
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
@RequestMapping("/api/v1/products")
@Tag(name = "상품 API", description = "상품 관련 API")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final RegisterProductUseCase registerProductUseCase;
    private final RegisterProductCategoriesUseCase registerProductCategoriesUseCase;
    private final GetProductUseCase getProductUseCase;

    @PostMapping
    @PreAuthorize(ADMIN_OR_SELLER_POINTCUT)
    @RegisterProductApiDocs
    public ResponseEntity<BaseResponse<RegisterProductResponse>> registerProduct(
            @Valid @RequestBody RegisterProductRequest request) {
        RegisterProductResult result = registerProductUseCase.registerProduct(
                ProductRequestToCommandMapper.mapToCommand(request));

        return new ResponseEntity<>(
                BaseResponse.of(
                        RegisterProductResponse.from(result),
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "상품 등록 성공"
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{productId}/categories")
    @PreAuthorize(ADMIN_OR_SELLER_POINTCUT)
    @RegisterProductCategoriesApiDocs
    public ResponseEntity<BaseResponse<RegisterProductCategoriesResponse>> registerProductCategories(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody RegisterProductCategoriesRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        RegisterProductCategoriesResult result = registerProductCategoriesUseCase.registerProductCategories(
                ElementExtractor.extractUserId(principal),
                AuthorityValidator.hasAdminRole(principal),
                ProductRequestToCommandMapper.mapToCommand(productId, request));

        return ResponseEntity.ok(
                BaseResponse.of(
                        RegisterProductCategoriesResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "상품 카테고리 등록 성공"
                )
        );
    }

    @GetMapping
    @GetProductsApiDocs
    public ResponseEntity<BaseResponse<GetProductsResponse>> getProducts(
            @RequestParam(value = "categoryId", required = false) Long categoryId) {
        GetProductsResult result = getProductUseCase.getProducts(categoryId);

        return ResponseEntity.ok(
                BaseResponse.of(
                        GetProductsResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "상품 목록 조회 성공"
                )
        );
    }
}
