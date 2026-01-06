package com.personal.marketnote.commerce.port.in.command.order;

import com.personal.marketnote.commerce.domain.order.OrderStatus;
import com.personal.marketnote.common.utility.FormatValidator;

import java.util.List;

public record ChangeOrderStatusCommand(
        Long id,
        List<Long> pricePolicyIds,
        OrderStatus orderStatus
) {
    public static ChangeOrderStatusCommand of(
            Long id,
            List<Long> pricePolicyIds,
            OrderStatus orderStatus
    ) {
        return new ChangeOrderStatusCommand(id, pricePolicyIds, orderStatus);
    }

    public boolean isPartialProductChange() {
        return FormatValidator.hasValue(pricePolicyIds);
    }
}

