package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.result.GetProductSortPropertiesResult;

public interface GetProductSortPropertiesUseCase {
    /**
     * @return 상품 정렬 속성 목록 조회 결과 {@link GetProductSortPropertiesResult}
     * @Date 2026-01-02
     * @Author 성효빈
     * @Description 상품 정렬 속성 목록을 조회합니다.
     */
    GetProductSortPropertiesResult getProductSortProperties();
}
