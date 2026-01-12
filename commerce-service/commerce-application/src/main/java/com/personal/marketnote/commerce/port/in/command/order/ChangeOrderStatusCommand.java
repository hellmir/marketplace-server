package com.personal.marketnote.commerce.port.in.command.order;

import com.personal.marketnote.commerce.domain.order.OrderStatus;
import com.personal.marketnote.commerce.domain.order.OrderStatusReasonCategory;
import com.personal.marketnote.common.utility.FormatValidator;

import java.util.List;

public record ChangeOrderStatusCommand(
        Long id,
        List<Long> pricePolicyIds,
        OrderStatus orderStatus,
        OrderStatusReasonCategory reasonCategory,
        String reason
) {
    public static ChangeOrderStatusCommand of(
            Long id,
            List<Long> pricePolicyIds,
            OrderStatus orderStatus,
            OrderStatusReasonCategory reasonCategory,
            String reason
    ) {
        return new ChangeOrderStatusCommand(id, pricePolicyIds, orderStatus, reasonCategory, reason);
    }

    public boolean isPartialProductChange() {
        return FormatValidator.hasValue(pricePolicyIds);
    }
}

