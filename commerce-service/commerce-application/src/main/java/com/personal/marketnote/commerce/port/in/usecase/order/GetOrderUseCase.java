package com.personal.marketnote.commerce.port.in.usecase.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.port.in.command.order.GetBuyerOrderHistoryQuery;
import com.personal.marketnote.commerce.port.in.result.order.GetOrderCountResult;
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
     * @param query 회원 주문 내역 조회 쿼리
     * @return 회원 주문 내역 조회 결과 {@link GetOrdersResult}
     * @Date 2026-01-05
     * @Author 성효빈
     * @Description 회원 주문 내역을 조회합니다.
     */
    GetOrdersResult getBuyerOrderHistory(GetBuyerOrderHistoryQuery query);

    /**
     * @param query 회원 주문 내역 조회 쿼리
     * @return 회원 주문 내역 개수 조회 결과 {@link GetOrderCountResult}
     * @Date 2026-01-19
     * @Author 성효빈
     * @Description 회원 주문 내역 개수를 조회합니다.
     */
    GetOrderCountResult getBuyerOrderCount(GetBuyerOrderHistoryQuery query);

    /**
     * @param orderId       주문 ID
     * @param pricePolicyId 가격 정책 ID
     * @return 주문 상품 도메인 {@link OrderProduct}
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 주문 상품 정보를 조회합니다.
     */
    OrderProduct getOrderProduct(Long orderId, Long pricePolicyId);
}
