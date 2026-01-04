package com.personal.marketnote.product.port.out.cart;

import com.personal.marketnote.product.domain.cart.CartProduct;

import java.util.Optional;

public interface FindCartProductPort {
    Optional<CartProduct> findCartProductByUserIdAndPricePolicyId(Long userId, Long pricePolicyId);
}
