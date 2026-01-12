package com.personal.marketnote.commerce.mapper;

import com.personal.marketnote.commerce.domain.order.OrderStatusHistoryCreateState;
import com.personal.marketnote.commerce.port.in.command.order.ChangeOrderStatusCommand;

public class OrderCommandToStateMapper {
    public static OrderStatusHistoryCreateState mapToState(ChangeOrderStatusCommand command) {
        return OrderStatusHistoryCreateState.builder()
                .orderId(command.id())
                .orderStatus(command.orderStatus())
                .reasonCategory(command.reasonCategory())
                .reason(command.reason())
                .build();
    }
}
