package com.personal.marketnote.commerce.service.order;

import com.personal.marketnote.commerce.port.in.command.order.RegisterOrderCommand;
import com.personal.marketnote.commerce.port.in.result.order.RegisterOrderResult;
import com.personal.marketnote.commerce.port.in.usecase.order.RegisterOrderUseCase;
import com.personal.marketnote.commerce.port.out.order.SaveOrderPort;
import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.order.Order;
import com.personal.marketnote.product.domain.order.OrderProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class RegisterOrderService implements RegisterOrderUseCase {
    private final SaveOrderPort saveOrderPort;

    @Override
    public RegisterOrderResult registerOrder(RegisterOrderCommand command) {
        List<OrderProduct> orderProducts = command.orderProducts().stream()
                .map(item -> OrderProduct.of(
                        item.pricePolicyId(),
                        item.quantity(),
                        item.unitAmount(),
                        item.imageUrl()
                ))
                .toList();

        Order savedOrder = saveOrderPort.save(
                Order.of(
                        command.sellerId(),
                        command.buyerId(),
                        command.totalAmount(),
                        command.couponAmount(),
                        command.pointAmount(),
                        orderProducts
                )
        );

        return RegisterOrderResult.from(savedOrder);
    }
}

