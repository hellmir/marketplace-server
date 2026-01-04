package com.personal.marketnote.product.adapter.out.persistence.pricepolicy;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionPricePolicyJpaRepository;
import com.personal.marketnote.product.port.in.result.pricepolicy.GetProductPricePolicyResult;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePoliciesPort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class PricePolicyReadPersistenceAdapter implements FindPricePoliciesPort {
    private final PricePolicyJpaRepository pricePolicyJpaRepository;
    private final ProductOptionPricePolicyJpaRepository productOptionPricePolicyJpaRepository;

    @Override
    public List<GetProductPricePolicyResult> findByProductId(Long productId) {
        List<PricePolicyJpaEntity> policies = pricePolicyJpaRepository.findAll().stream()
                .filter(pp -> pp.getProductJpaEntity().getId().equals(productId))
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .toList();
        List<GetProductPricePolicyResult> results = new ArrayList<>();
        for (PricePolicyJpaEntity pp : policies) {
            List<Long> optionIds = productOptionPricePolicyJpaRepository.findOptionIdsByPricePolicyId(pp.getId());
            results.add(
                    new GetProductPricePolicyResult(
                            pp.getId(),
                            pp.getPrice(),
                            pp.getDiscountPrice(),
                            pp.getAccumulatedPoint(),
                            pp.getDiscountRate(),
                            optionIds
                    )
            );
        }
        return results;
    }
}


