package com.personal.marketnote.commerce.port.in.command;

import java.util.List;

public record RegisterOrderCommand(
        Long sellerId,
        Long buyerId,
        Long totalAmount,
        Long couponAmount,
        Long pointAmount,
        List<OrderProductItem> orderProducts
) {
    public record OrderProductItem(
            Long pricePolicyId,
            Integer quantity,
            Long unitAmount,
            String imageUrl
    ) {
    }

    public static RegisterOrderCommand of(
            Long sellerId,
            Long buyerId,
            Long totalAmount,
            Long couponAmount,
            Long pointAmount,
            List<OrderProductItem> orderProducts
    ) {
        return new RegisterOrderCommand(
                sellerId, buyerId, totalAmount, couponAmount, pointAmount, orderProducts
        );
    }
}

