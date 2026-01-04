package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.GetMyOrderingProductsCommand;
import com.personal.marketnote.product.port.in.result.cart.GetMyCartProductsResult;
import com.personal.marketnote.product.port.in.result.product.GetMyOrderProductsResult;

public interface GetMyOrderingProductsUseCase {
    /**
     * @param getMyOrderingProductsCommand 주문 대기 상품 목록
     * @return 주문 대기 상품 목록 {@link GetMyCartProductsResult}
     * @Date 2026-01-05
     * @Author 성효빈
     * @Description 회원의 주문 대기 상품 목록을 조회합니다.
     */
    GetMyOrderProductsResult getMyOrderingProducts(GetMyOrderingProductsCommand getMyOrderingProductsCommand);
}
