package com.personal.marketnote.commerce.service.order;

import com.personal.marketnote.commerce.domain.order.Order;
import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.domain.order.OrderStatus;
import com.personal.marketnote.commerce.domain.order.OrderStatusHistory;
import com.personal.marketnote.commerce.exception.OrderStatusAlreadyChangedException;
import com.personal.marketnote.commerce.port.in.command.order.ChangeOrderStatusCommand;
import com.personal.marketnote.commerce.port.in.usecase.inventory.ReduceProductInventoryUseCase;
import com.personal.marketnote.commerce.port.in.usecase.order.ChangeOrderStatusUseCase;
import com.personal.marketnote.commerce.port.in.usecase.order.GetOrderUseCase;
import com.personal.marketnote.commerce.port.out.order.DeleteOrderedCartProductsPort;
import com.personal.marketnote.commerce.port.out.order.UpdateOrderPort;
import com.personal.marketnote.common.application.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class ChangeOrderStatusService implements ChangeOrderStatusUseCase {
    private final ReduceProductInventoryUseCase reduceProductInventoryUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final UpdateOrderPort updateOrderPort;
    private final DeleteOrderedCartProductsPort deleteOrderedCartProductsPort;

    @Override
    public void changeOrderStatus(ChangeOrderStatusCommand command) {
        Order order = getOrderUseCase.getOrder(command.id());
        OrderStatus status = command.orderStatus();

        if (status.isMe(order.getOrderStatus())) {
            throw new OrderStatusAlreadyChangedException(status);
        }

        changeOrderStatus(command, order);
        OrderStatusHistory orderStatusHistory = OrderStatusHistory.of(command.id(), command.orderStatus(), command.reason());
        updateOrderPort.update(order, orderStatusHistory);

        // FIXME: Payment Service의 Kafka 이벤트 Consumption으로 변경(주문 상태 PAID로 변경 / 결제 금액 업데이트 / 재고 감소 / 장바구니 상품 삭제)
        if (status.isPaid()) {
            // 결제 완료 시 재고 차감
            reduceProductInventoryUseCase.reduce(order.getOrderProducts(), status.getDescription());

            // 결제 완료 시 장바구니 상품 삭제
            deleteOrderedCartProductsPort.delete(
                    order.getOrderProducts()
                            .stream()
                            .map(OrderProduct::getPricePolicyId)
                            .toList()
            );
        }
    }

    private void changeOrderStatus(ChangeOrderStatusCommand command, Order order) {
        OrderStatus status = command.orderStatus();

        if (command.isPartialProductChange()) {
            order.changeProductsStatus(command.pricePolicyIds(), status);
            return;
        }

        order.changeAllProductsStatus(status);
    }
}
