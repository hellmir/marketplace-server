package com.personal.marketnote.product.adapter.out.persistence;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.repository.ProductJpaRepository;
import com.personal.marketnote.product.domain.product.PricePolicy;
import com.personal.marketnote.product.port.out.pricepolicy.SavePricePolicyPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class PricePolicyPersistenceAdapter implements SavePricePolicyPort {
    private final ProductJpaRepository productJpaRepository;
    private final PricePolicyJpaRepository pricePolicyJpaRepository;

    @Override
    public Long save(PricePolicy pricePolicy) {
        ProductJpaEntity productRef = productJpaRepository.getReferenceById(pricePolicy.getProduct().getId());
        PricePolicyJpaEntity saved = pricePolicyJpaRepository.save(PricePolicyJpaEntity.from(productRef, pricePolicy));

        return saved.getId();
    }
}


