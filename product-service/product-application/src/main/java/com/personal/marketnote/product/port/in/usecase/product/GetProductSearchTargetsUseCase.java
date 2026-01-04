package com.personal.marketnote.product.port.in.usecase.product;

import com.personal.marketnote.product.port.in.result.product.GetProductSearchTargetsResult;

public interface GetProductSearchTargetsUseCase {
    /**
     * @return 상품 검색 대상 목록 조회 결과 {@link GetProductSearchTargetsResult}
     * @Date 2026-01-02
     * @Author 성효빈
     * @Description 상품 검색 대상 목록을 조회합니다.
     */
    GetProductSearchTargetsResult getProductSearchTargets();
}
