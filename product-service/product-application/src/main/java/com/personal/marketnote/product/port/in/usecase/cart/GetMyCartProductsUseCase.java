package com.personal.marketnote.product.port.in.usecase.cart;

import com.personal.marketnote.product.port.in.result.cart.GetMyCartProductsResult;

public interface GetMyCartProductsUseCase {
    /**
     * @param userId 회원 ID
     * @Date 2026-01-04
     * @Author 성효빈
     * @Description 회원의 장바구니 상품 목록을 조회합니다.
     */
    GetMyCartProductsResult getMyCartProducts(Long userId);
}
