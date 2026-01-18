package com.personal.marketnote.product.port.in.command;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record OrderingItemQuery(
        Long pricePolicyId,
        Long sharerId,
        Short quantity,
        String imageUrl
) {
    public static OrderingItemQuery of(Long pricePolicyId, Long sharerId, Short quantity, String imageUrl) {
        return OrderingItemQuery.builder()
                .pricePolicyId(pricePolicyId)
                .sharerId(sharerId)
                .quantity(quantity)
                .imageUrl(imageUrl)
                .build();
    }
}
