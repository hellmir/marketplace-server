package com.personal.marketnote.commerce.port.in.usecase.inventory;

import com.personal.marketnote.commerce.domain.order.OrderProduct;

import java.util.List;

public interface ReduceProductInventoryUseCase {
    /**
     * @param orderProducts 주문 상품 목록
     * @Date 2026-01-06
     * @Author 성효빈
     * @Description 주문 상품의 재고를 감소시킵니다.
     */
    void reduce(List<OrderProduct> orderProducts);
}
