package com.personal.marketnote.product.port.in.command;

import lombok.Builder;

@Builder
public record AddCartProductCommand(
        Long userId,
        Long sharerId,
        Long pricePolicyId,
        String imageUrl,
        Short quantity
) {
}
