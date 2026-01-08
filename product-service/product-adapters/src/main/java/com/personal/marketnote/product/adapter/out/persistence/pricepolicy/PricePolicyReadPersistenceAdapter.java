package com.personal.marketnote.product.adapter.out.persistence.pricepolicy;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.mapper.PricePolicyJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionPricePolicyJpaRepository;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePoliciesPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class PricePolicyReadPersistenceAdapter implements FindPricePoliciesPort {
    private final PricePolicyJpaRepository pricePolicyJpaRepository;
    private final ProductOptionPricePolicyJpaRepository productOptionPricePolicyJpaRepository;

    @Override
    public List<PricePolicy> findByProductId(Long productId) {
        List<PricePolicyJpaEntity> policyEntities = pricePolicyJpaRepository.findAll().stream()
                .filter(policyEntity -> policyEntity.getProductJpaEntity().getId().equals(productId))
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .toList();

        return policyEntities.stream()
                .map(policyEntity -> PricePolicyJpaEntityToDomainMapper.mapToDomain(
                        policyEntity, productOptionPricePolicyJpaRepository.findOptionIdsByPricePolicyId(policyEntity.getId())
                ))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public List<PricePolicy> findByIds(List<Long> ids) {
        return pricePolicyJpaRepository.findAllById(ids).stream()
                .map(
                        policyEntity -> PricePolicyJpaEntityToDomainMapper.mapToDomain(
                                policyEntity,
                                productOptionPricePolicyJpaRepository.findOptionIdsByPricePolicyId(policyEntity.getId())
                        )
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}


