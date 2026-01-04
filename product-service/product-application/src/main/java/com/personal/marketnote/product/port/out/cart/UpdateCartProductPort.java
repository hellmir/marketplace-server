package com.personal.marketnote.product.port.out.cart;

import com.personal.marketnote.product.domain.cart.CartProduct;

public interface UpdateCartProductPort {
    void update(CartProduct cartProduct, Long originalPricePolicyId);
}
