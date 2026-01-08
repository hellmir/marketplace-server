package com.personal.marketnote.product.port.in.usecase.product;

import java.util.List;
import java.util.Map;

public interface GetProductInventoryUseCase {
    /**
     * @param pricePolicyIds 가격 정책 ID 목록
     * @return 상품 재고 목록 {@link Map<Long, Integer>}
     * @Date 2026-01-08
     * @Author 성효빈
     * @Description 가격 정책 ID 목록에 해당하는 상품 재고 목록을 조회합니다.
     */
    Map<Long, Integer> getProductStocks(List<Long> pricePolicyIds);
}
