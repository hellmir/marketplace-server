package com.personal.marketnote.commerce.port.in.usecase.inventory;

import com.personal.marketnote.commerce.domain.order.OrderProduct;

import java.util.List;

public interface ReduceProductInventoryUseCase {
    /**
     * @param orderProducts 주문 상품 목록
     * @param reason        재고 차감 이유
     * @Date 2026-01-06
     * @Author 성효빈
     * @Description 주문 상품의 재고를 차감합니다. 분산 락/낙관적 락 이중 동시성 제어 로직이 적용되어 있습니다.
     */
    void reduce(List<OrderProduct> orderProducts, String reason);
}
