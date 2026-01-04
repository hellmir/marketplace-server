package com.personal.marketnote.product.adapter.out.persistence.cart;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.mapper.CartJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.cart.entity.CartProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.cart.repository.CartJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository;
import com.personal.marketnote.product.domain.cart.CartProduct;
import com.personal.marketnote.product.port.out.cart.FindCartProductPort;
import com.personal.marketnote.product.port.out.cart.FindCartProductsPort;
import com.personal.marketnote.product.port.out.cart.SaveCartProductPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class CartPersistenceAdapter implements SaveCartProductPort, FindCartProductsPort, FindCartProductPort {
    private final CartJpaRepository cartJpaRepository;
    private final PricePolicyJpaRepository pricePolicyJpaRepository;

    @Override
    public CartProduct save(CartProduct cartProduct) {
        PricePolicyJpaEntity pricePolicyJpaEntity
                = pricePolicyJpaRepository.getReferenceById(cartProduct.getPricePolicy().getId());
        CartProductJpaEntity savedEntity
                = cartJpaRepository.save(CartProductJpaEntity.from(cartProduct, pricePolicyJpaEntity));

        return CartJpaEntityToDomainMapper.mapToDomain(savedEntity).orElse(null);
    }

    @Override
    public List<CartProduct> findByUserId(Long userId) {
        List<CartProductJpaEntity> cartProductJpaEntities = cartJpaRepository.findByIdUserId(userId);
        return cartProductJpaEntities.stream()
                .map(CartJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public Optional<CartProduct> findCartProductByUserIdAndPricePolicyId(Long userId, Long pricePolicyId) {
        return CartJpaEntityToDomainMapper.mapToDomain(
                cartJpaRepository.findByIdUserIdAndPricePolicyId(userId, pricePolicyId).orElse(null)
        );
    }
}
