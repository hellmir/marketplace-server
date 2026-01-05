package com.personal.marketnote.order.service.order;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.order.port.in.command.ChangeOrderStatusCommand;
import com.personal.marketnote.order.port.in.usecase.order.ChangeOrderStatusUseCase;
import com.personal.marketnote.order.port.in.usecase.order.GetOrderUseCase;
import com.personal.marketnote.order.port.out.order.UpdateOrderPort;
import com.personal.marketnote.product.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class ChangeOrderStatusService implements ChangeOrderStatusUseCase {
    private final GetOrderUseCase getOrderUseCase;
    private final UpdateOrderPort updateOrderPort;

    @Override
    public void changeOrderStatus(ChangeOrderStatusCommand command) {
        Order order = getOrderUseCase.getOrder(command.id());

        if (command.isPartialProductChange()) {
            order.changeProductsStatus(command.pricePolicyIds(), command.orderStatus());
            return;
        }

        order.changeAllProductsStatus(command.orderStatus());
        updateOrderPort.update(order);
    }
}
