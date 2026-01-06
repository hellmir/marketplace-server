package com.personal.marketnote.commerce.service.order;

import com.personal.marketnote.commerce.port.in.command.order.ChangeOrderStatusCommand;
import com.personal.marketnote.commerce.port.in.usecase.inventory.ReduceProductStockUseCase;
import com.personal.marketnote.commerce.port.in.usecase.order.ChangeOrderStatusUseCase;
import com.personal.marketnote.commerce.port.in.usecase.order.GetOrderUseCase;
import com.personal.marketnote.commerce.port.out.order.DeleteOrderedCartProductsPort;
import com.personal.marketnote.commerce.port.out.order.UpdateOrderPort;
import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.order.Order;
import com.personal.marketnote.product.domain.order.OrderProduct;
import com.personal.marketnote.product.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class ChangeOrderStatusService implements ChangeOrderStatusUseCase {
    private final ReduceProductStockUseCase reduceProductStockUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final UpdateOrderPort updateOrderPort;
    private final DeleteOrderedCartProductsPort deleteOrderedCartProductsPort;

    @Override
    public void changeOrderStatus(ChangeOrderStatusCommand command) {
        Order order = getOrderUseCase.getOrder(command.id());
        OrderStatus status = command.orderStatus();

        if (command.isPartialProductChange()) {
            order.changeProductsStatus(command.pricePolicyIds(), status);
            return;
        }

        order.changeAllProductsStatus(status);
        updateOrderPort.update(order);

        // FIXME: Payment Service의 Kafka 이벤트 Consumption으로 변경(주문 상태 PAID로 변경 / 재고 감소 / 장바구니 상품 삭제)
        if (status.isPaid()) {
            // 결제 완료 시 재고 차감
            reduceProductStockUseCase.reduce(order.getOrderProducts());

            // 결제 완료 시 장바구니 상품 삭제
            deleteOrderedCartProductsPort.delete(
                    order.getOrderProducts()
                            .stream()
                            .map(OrderProduct::getPricePolicyId)
                            .toList()
            );
        }
    }
}
