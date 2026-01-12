package com.personal.marketnote.commerce.port.in.command.order;

import com.personal.marketnote.commerce.domain.order.OrderStatus;
import com.personal.marketnote.commerce.domain.order.OrderStatusReasonCategory;
import com.personal.marketnote.common.utility.FormatValidator;
import lombok.Builder;

import java.util.List;

@Builder
public record ChangeOrderStatusCommand(
        Long id,
        List<Long> pricePolicyIds,
        OrderStatus orderStatus,
        OrderStatusReasonCategory reasonCategory,
        String reason
) {
    public boolean isPartialProductChange() {
        return FormatValidator.hasValue(pricePolicyIds);
    }
}

