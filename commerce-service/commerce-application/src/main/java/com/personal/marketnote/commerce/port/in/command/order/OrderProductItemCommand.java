package com.personal.marketnote.commerce.port.in.command.order;

import lombok.Builder;

@Builder
public record OrderProductItemCommand(
        Long sellerId,
        Long pricePolicyId,
        Long sharerId,
        Integer quantity,
        Long unitAmount,
        String imageUrl
) {
}
