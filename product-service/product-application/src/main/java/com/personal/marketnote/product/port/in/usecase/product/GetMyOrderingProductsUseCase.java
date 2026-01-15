package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.command.GetMyOrderingProductsQuery;
import com.personal.marketnote.product.port.in.result.product.GetMyOrderProductsResult;

public interface GetMyOrderingProductsUseCase {
    /**
     * @param getMyOrderingProductsQuery 주문 대기 상품 목록 조회 쿼리
     * @return 주문 대기 상품 목록 조회 결과 {@link GetMyOrderProductsResult}
     * @Date 2026-01-16
     * @Author 성효빈
     * @Description 주문 대기 상품 목록을 조회합니다.
     */
    GetMyOrderProductsResult getMyOrderingProducts(GetMyOrderingProductsQuery getMyOrderingProductsQuery);
}
