package com.personal.marketnote.product.adapter.out.mapper;

import com.personal.marketnote.product.adapter.out.persistence.cart.entity.CartProductJpaEntity;
import com.personal.marketnote.product.domain.cart.CartProduct;

import java.util.Optional;

public class CartJpaEntityToDomainMapper {
    public static Optional<CartProduct> mapToDomain(CartProductJpaEntity cartProductJpaEntity) {
        return Optional.ofNullable(cartProductJpaEntity)
                .map(
                        entity -> CartProduct.of(
                                cartProductJpaEntity.getId().getUserId(),
                                cartProductJpaEntity.getId().getProductId(),
                                cartProductJpaEntity.getId().getPricePolicyId(),
                                cartProductJpaEntity.getQuantity(),
                                cartProductJpaEntity.getStatus()
                        )
                );
    }
}
