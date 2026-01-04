package com.personal.marketnote.product.adapter.in.client.cart.controller;

import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.utility.ElementExtractor;
import com.personal.marketnote.product.adapter.in.client.cart.mapper.CartRequestToCommandMapper;
import com.personal.marketnote.product.adapter.in.client.cart.request.AddCartProductRequest;
import com.personal.marketnote.product.adapter.in.client.cart.request.UpdateCartProductOptionsRequest;
import com.personal.marketnote.product.adapter.in.client.cart.request.UpdateCartProductQuantityRequest;
import com.personal.marketnote.product.adapter.in.client.cart.response.GetMyCartProductsResponse;
import com.personal.marketnote.product.adapter.in.client.pricepolicy.AddCartProductApiDocs;
import com.personal.marketnote.product.adapter.in.client.pricepolicy.GetMyCartProductsApiDocs;
import com.personal.marketnote.product.adapter.in.client.pricepolicy.UpdateCartProductOptionApiDocs;
import com.personal.marketnote.product.adapter.in.client.pricepolicy.UpdateCartProductQuantityApiDocs;
import com.personal.marketnote.product.port.in.result.cart.GetMyCartProductsResult;
import com.personal.marketnote.product.port.in.usecase.cart.AddCartProductUseCase;
import com.personal.marketnote.product.port.in.usecase.cart.GetMyCartProductsUseCase;
import com.personal.marketnote.product.port.in.usecase.cart.UpdateCartProductOptionsUseCase;
import com.personal.marketnote.product.port.in.usecase.cart.UpdateCartProductQuantityUseCase;
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
    private final UpdateCartProductQuantityUseCase updateCartProductQuantityUseCase;
    private final UpdateCartProductOptionsUseCase updateCartProductOptionsUseCase;

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

    /**
     * 장바구니 상품 수량 변경
     *
     * @Author 성효빈
     * @Date 2026-01-04
     * @Description 장바구니 상품 수량을 변경합니다.
     */
    @PatchMapping("/quantity")
    @UpdateCartProductQuantityApiDocs
    public ResponseEntity<BaseResponse<Void>> updateCartProductQuantity(
            @Valid @RequestBody UpdateCartProductQuantityRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        updateCartProductQuantityUseCase.updateCartProductQuantity(
                CartRequestToCommandMapper.mapToCommand(ElementExtractor.extractUserId(principal), request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "장바구니 상품 수량 변경 성공"
                ),
                HttpStatus.OK
        );
    }

    /**
     * 장바구니 상품 옵션 변경
     *
     * @Author 성효빈
     * @Date 2026-01-04
     * @Description 장바구니 상품 옵션을 변경합니다.
     */
    @PatchMapping("/options")
    @UpdateCartProductOptionApiDocs
    public ResponseEntity<BaseResponse<Void>> updateCartProductOptions(
            @Valid @RequestBody UpdateCartProductOptionsRequest request,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) {
        updateCartProductOptionsUseCase.updateCartProductOptions(
                CartRequestToCommandMapper.mapToCommand(ElementExtractor.extractUserId(principal), request)
        );

        return new ResponseEntity<>(
                BaseResponse.of(
                        null,
                        HttpStatus.OK,
                        DEFAULT_SUCCESS_CODE,
                        "장바구니 상품 옵션 변경 성공"
                ),
                HttpStatus.OK
        );
    }
}
