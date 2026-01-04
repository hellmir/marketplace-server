package com.personal.marketnote.product.port.in.command;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record OrderingItemCommand(
        Long pricePolicyId,
        Short quantity,
        String imageUrl
) {
    public static OrderingItemCommand of(Long pricePolicyId, Short quantity, String imageUrl) {
        return OrderingItemCommand.builder()
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .imageUrl(imageUrl)
                .build();
    }
}
