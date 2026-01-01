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
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_OR_SELLER_POINTCUT;
import static com.personal.marketnote.common.utility.ApiConstant.ADMIN_OR_SELLER_PRINCIPAL_POINTCUT;
import static com.personal.marketnote.common.utility.NumberConstant.MINUS_ONE;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "상품 API", description = "상품 관련 API")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private static final String GET_PRODUCTS_DEFAULT_PAGE_SIZE = "4";

    private final RegisterProductUseCase registerProductUseCase;
    private final RegisterProductCategoriesUseCase registerProductCategoriesUseCase;
    private final GetProductUseCase getProductUseCase;

    @PostMapping
    @PreAuthorize(ADMIN_OR_SELLER_PRINCIPAL_POINTCUT)
    @RegisterProductApiDocs
    public ResponseEntity<BaseResponse<RegisterProductResponse>> registerProduct(
            @Valid @RequestBody RegisterProductRequest request
    ) {
        RegisterProductResult result = registerProductUseCase.registerProduct(
                ProductRequestToCommandMapper.mapToCommand(request)
        );

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

    /**
     * 상품 목록 조회
     *
     * @param cursor
     * @param pageSize      페이지 크기
     * @param sortDirection 정렬 방향
     * @param sortProperty  정렬 속성
     * @param searchTarget  검색 대상
     * @param searchKeyword 검색 키워드
     * @return 회원 목록 조회 응답 {@link GetProductsResponse}
     * @Author 성효빈
     * @Date 2025-12-31
     * @Description 상품 목록을 조회합니다.
     */
    @GetMapping
    @GetProductsApiDocs
    public ResponseEntity<BaseResponse<GetProductsResponse>> getProducts(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "cursor", required = false, defaultValue = MINUS_ONE) Long cursor,
            @RequestParam(value = "page-size", defaultValue = GET_PRODUCTS_DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction sortDirection,
            @RequestParam(required = false, defaultValue = "ORDER_NUM") ProductSortProperty sortProperty,
            @RequestParam(required = false, defaultValue = "NAME") ProductSearchTarget searchTarget,
            @RequestParam(required = false) String searchKeyword
    ) {
        GetProductsResult result = getProductUseCase.getProducts(
                categoryId,
                cursor,
                pageSize,
                sortDirection,
                sortProperty,
                searchTarget,
                searchKeyword
        );

        return ResponseEntity.ok(
                BaseResponse.of(
                        GetProductsResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "상품 목록 조회 성공"
                )
        );
    }

    @PutMapping("{productId}/categories")
    @PreAuthorize(ADMIN_OR_SELLER_POINTCUT)
    @RegisterProductCategoriesApiDocs
    public ResponseEntity<BaseResponse<RegisterProductCategoriesResponse>> registerProductCategories(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody RegisterProductCategoriesRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        RegisterProductCategoriesResult result = registerProductCategoriesUseCase.registerProductCategories(
                ElementExtractor.extractUserId(principal),
                AuthorityValidator.hasAdminRole(principal),
                ProductRequestToCommandMapper.mapToCommand(productId, request
                )
        );

        return ResponseEntity.ok(
                BaseResponse.of(
                        RegisterProductCategoriesResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "상품 카테고리 등록 성공"
                )
        );
    }
}
