package com.personal.marketnote.commerce.port.in.command.order;

import lombok.Builder;

@Builder
public record OrderProductItem(
        Long pricePolicyId,
        Integer quantity,
        Long unitAmount,
        String imageUrl
) {
}