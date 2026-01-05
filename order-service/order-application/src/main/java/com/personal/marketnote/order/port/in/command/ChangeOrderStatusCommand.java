package com.personal.marketnote.order.port.in.command;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.order.OrderStatus;

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

