package com.personal.marketnote.commerce.port.in.usecase.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.port.in.command.order.GetBuyerOrderHistoryCommand;
import com.personal.marketnote.commerce.port.in.result.order.GetOrdersResult;

public interface GetOrderUseCase {
    /**
     * @param id 주문 ID
     * @return 주문 도메인 {@link Order}
     * @Date 2026-01-05
     * @Author 성효빈
     * @Description 주문 정보를 조회합니다.
     */
    Order getOrder(Long id);

    GetOrdersResult getBuyerOrderHistory(GetBuyerOrderHistoryCommand query);
}
