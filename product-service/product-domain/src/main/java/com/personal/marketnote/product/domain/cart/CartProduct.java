package com.personal.marketnote.product.domain.cart;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.domain.BaseDomain;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CartProduct extends BaseDomain {
    private Long userId;
    private Long productId;
    private Long pricePolicyId;
    private Short quantity;

    public static CartProduct of(Long userId, Long productId, Long pricePolicyId, Short quantity) {
        CartProduct cartProduct = CartProduct.builder()
                .userId(userId)
                .productId(productId)
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .build();
        cartProduct.activate();

        return cartProduct;
    }

    public static CartProduct of(Long userId, Long productId, Long pricePolicyId, Short quantity, EntityStatus status) {
        return CartProduct.builder()
                .userId(userId)
                .productId(productId)
                .pricePolicyId(pricePolicyId)
                .quantity(quantity)
                .build();
    }
}
