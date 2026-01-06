package com.personal.marketnote.order.service.order;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.order.exception.OrderNotFoundException;
import com.personal.marketnote.order.port.in.usecase.order.GetOrderUseCase;
import com.personal.marketnote.order.port.out.order.FindOrderPort;
import com.personal.marketnote.product.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetOrderService implements GetOrderUseCase {
    private final FindOrderPort findOrderPort;

    @Override
    public Order getOrder(Long id) {
        return findOrderPort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<Order> getOrders(Long buyerId) {
        return findOrderPort.findByBuyerId(buyerId);
    }
}
