package com.personal.marketnote.product.adapter.in.client.cart.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.product.adapter.in.client.cart.mapper.CartRequestToCommandMapper;
import com.personal.marketnote.product.adapter.in.client.cart.request.AddCartProductRequest;
import com.personal.marketnote.product.adapter.in.client.cart.response.GetMyCartProductsResponse;
import com.personal.marketnote.product.adapter.in.client.pricepolicy.AddCartProductApiDocs;
import com.personal.marketnote.product.adapter.in.client.pricepolicy.GetMyCartProductsApiDocs;
import com.personal.marketnote.product.port.in.result.cart.GetMyCartProductsResult;
import com.personal.marketnote.product.port.in.usecase.cart.AddCartProductUseCase;
import com.personal.marketnote.product.port.in.usecase.cart.GetMyCartProductsUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.DEFAULT_SUCCESS_CODE;

@RestController
@RequestMapping("/api/v1/carts")
@Tag(name = "장바구니 API", description = "장바구니 관련 API")
@RequiredArgsConstructor
public class CartController {
    private final AddCartProductUseCase addCartProductUseCase;
    private final GetMyCartProductsUseCase getMyCartProductsUseCase;

    /**
     * 장바구니 상품 추가
     *
     * @param request 장바구니 상품 추가 요청
     * @Author 성효빈
     * @Date 2026-01-04
     * @Description 장바구니에 상품을 추가합니다.
     */
    @PostMapping
    @AddCartProductApiDocs
    public ResponseEntity<BaseResponse<Void>> addCartProduct(
            @Valid @RequestBody AddCartProductRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        addCartProductUseCase.addCartProduct(
                CartRequestToCommandMapper.mapToCommand(ElementExtractor.extractUserId(principal), request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.CREATED,
                        DEFAULT_SUCCESS_CODE,
                        "장바구니 상품 추가 성공"
                ),
                HttpStatus.CREATED
        );
    }

    /**
     * 회원 장바구니 상품 목록 조회
     *
     * @Author 성효빈
     * @Date 2026-01-04
     * @Description 회원의 장바구니 상품 목록을 조회합니다.
     */
    @GetMapping
    @GetMyCartProductsApiDocs
    public ResponseEntity<BaseResponse<GetMyCartProductsResponse>> getMyCartProducts(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        GetMyCartProductsResult result = getMyCartProductsUseCase.getMyCartProducts(
                ElementExtractor.extractUserId(principal)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        GetMyCartProductsResponse.from(result),
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "회원 장바구니 상품 목록 조회 성공"
                ),
                HttpStatus.OK
        );
    }
}
