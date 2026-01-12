package com.personal.marketnote.commerce.port.in.usecase.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.port.in.command.order.GetBuyerOrderHistoryCommand;
import com.personal.marketnote.commerce.port.in.result.order.GetOrderResult;
import com.personal.marketnote.commerce.port.in.result.order.GetOrdersResult;

public interface GetOrderUseCase {
    /**
     * @param id 주문 ID
     * @return 주문 조회 결과 {@link GetOrderResult}
     * @Date 2026-01-05
     * @Author 성효빈
     * @Description 주문과 주문 상품 정보를 조회합니다.
     */
    GetOrderResult getOrderAndOrderProducts(Long id);

    /**
     * @param id 주문 ID
     * @return 주문 도메인 {@link Order}
     * @Date 2026-01-05
     * @Author 성효빈
     * @Description 주문 정보를 조회합니다.
     */
    Order getOrder(Long id);

    /**
     * @param command 회원 주문 내역 조회 커맨드
     * @return 회원 주문 내역 조회 결과 {@link GetOrdersResult}
     * @Date 2026-01-05
     * @Author 성효빈
     * @Description 회원 주문 내역을 조회합니다.
     */
    GetOrdersResult getBuyerOrderHistory(GetBuyerOrderHistoryCommand command);
}
