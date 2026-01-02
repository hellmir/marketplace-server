package com.personal.marketnote.product.adapter.out.persistence.pricepolicy;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionPricePolicyJpaRepository;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePolicyValuesPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class PricePolicyQueryPersistenceAdapter implements FindPricePolicyValuesPort {
    private final PricePolicyJpaRepository pricePolicyJpaRepository;
    private final ProductOptionPricePolicyJpaRepository productOptionPricePolicyJpaRepository;

    @Override
    public Optional<PricePolicyValues> findByProductAndOptionIds(Long productId, List<Long> optionIds) {
        if (optionIds == null || optionIds.isEmpty()) {
            return Optional.empty();
        }
        long size = optionIds.size();
        List<Long> candidates = productOptionPricePolicyJpaRepository.findCandidatePricePolicyIds(optionIds, size);
        for (Long policyId : candidates) {
            long totalMapped = productOptionPricePolicyJpaRepository.countByPricePolicyJpaEntity_Id(policyId);
            if (totalMapped != size) continue;
            PricePolicyJpaEntity pp = pricePolicyJpaRepository.findById(policyId).orElse(null);
            if (pp == null) continue;
            if (!pp.getProductJpaEntity().getId().equals(productId)) continue;
            return Optional.of(new PricePolicyValues(
                    pp.getPrice(),
                    pp.getDiscountPrice(),
                    pp.getAccumulatedPoint(),
                    pp.getDiscountRate()
            ));
        }
        return Optional.empty();
    }
}


