package com.personal.marketnote.product.domain.cart;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.domain.BaseDomain;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CartProduct extends BaseDomain {
    private Long userId;
    private PricePolicy pricePolicy;
    private String imageUrl;
    private Short quantity;

    public static CartProduct of(Long userId, PricePolicy pricePolicy, String imageUrl, Short quantity) {
        CartProduct cartProduct = CartProduct.builder()
                .userId(userId)
                .pricePolicy(pricePolicy)
                .imageUrl(imageUrl)
                .quantity(quantity)
                .build();
        cartProduct.activate();

        return cartProduct;
    }

    public static CartProduct of(
            Long userId, PricePolicy pricePolicy, String imageUrl, Short quantity, EntityStatus status
    ) {
        CartProduct cartProduct = CartProduct.builder()
                .userId(userId)
                .pricePolicy(pricePolicy)
                .imageUrl(imageUrl)
                .quantity(quantity)
                .build();

        if (status.isActive()) {
            cartProduct.activate();
            return cartProduct;
        }

        if (status.isInactive()) {
            cartProduct.deactivate();
            return cartProduct;
        }

        cartProduct.hide();
        return cartProduct;
    }

    public Long getPricePolicyId() {
        return pricePolicy.getId();
    }

    public void updateQuantity(Short newQuantity) {
        quantity = newQuantity;
    }

    public void updatePricePolicy(PricePolicy pricePolicy) {
        this.pricePolicy = pricePolicy;
    }
}
