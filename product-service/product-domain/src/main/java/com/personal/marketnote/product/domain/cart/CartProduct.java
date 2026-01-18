package com.personal.marketnote.product.domain.cart;

import com.personal.marketnote.common.domain.BaseDomain;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CartProduct extends BaseDomain {
    private Long userId;
    private Long sharerId;
    private PricePolicy pricePolicy;
    private String imageUrl;
    private Short quantity;

    public static CartProduct from(CartProductCreateState state) {
        CartProduct cartProduct = CartProduct.builder()
                .userId(state.getUserId())
                .sharerId(state.getSharerId())
                .pricePolicy(state.getPricePolicy())
                .imageUrl(state.getImageUrl())
                .quantity(state.getQuantity())
                .build();
        cartProduct.activate();

        return cartProduct;
    }

    public static CartProduct from(CartProductSnapshotState state) {
        CartProduct cartProduct = CartProduct.builder()
                .userId(state.getUserId())
                .sharerId(state.getSharerId())
                .pricePolicy(state.getPricePolicy())
                .imageUrl(state.getImageUrl())
                .quantity(state.getQuantity())
                .build();
        cartProduct.status = state.getStatus();

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
