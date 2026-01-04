package com.personal.marketnote.product.port.out.cart;

import com.personal.marketnote.product.domain.cart.CartProduct;

import java.util.List;

public interface FindCartProductsPort {
    List<CartProduct> findByUserId(Long userId);
}
