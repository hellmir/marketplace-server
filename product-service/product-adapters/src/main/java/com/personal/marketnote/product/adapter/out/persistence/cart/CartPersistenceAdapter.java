package com.personal.marketnote.product.adapter.out.persistence.cart;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.mapper.CartJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.cart.entity.CartProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.cart.repository.CartJpaRepository;
import com.personal.marketnote.product.domain.cart.CartProduct;
import com.personal.marketnote.product.port.out.cart.SaveCartProductPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CartPersistenceAdapter implements SaveCartProductPort {
    private final CartJpaRepository cartJpaRepository;

    @Override
    public CartProduct save(CartProduct cartProduct) {
        CartProductJpaEntity savedEntity = cartJpaRepository.save(CartProductJpaEntity.from(cartProduct));

        return CartJpaEntityToDomainMapper.mapToDomain(savedEntity).orElse(null);
    }
}
