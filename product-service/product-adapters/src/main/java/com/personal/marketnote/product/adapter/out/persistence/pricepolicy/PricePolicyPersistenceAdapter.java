package com.personal.marketnote.product.adapter.out.persistence.pricepolicy;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.repository.ProductJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionPricePolicyJpaRepository;
import com.personal.marketnote.product.domain.product.PricePolicy;
import com.personal.marketnote.product.port.out.pricepolicy.DeletePricePolicyPort;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePolicyPort;
import com.personal.marketnote.product.port.out.pricepolicy.SavePricePolicyPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class PricePolicyPersistenceAdapter implements SavePricePolicyPort, DeletePricePolicyPort, FindPricePolicyPort {
    private final ProductJpaRepository productJpaRepository;
    private final PricePolicyJpaRepository pricePolicyJpaRepository;
    private final ProductOptionPricePolicyJpaRepository productOptionPricePolicyJpaRepository;

    @Override
    public Long save(PricePolicy pricePolicy) {
        ProductJpaEntity productRef = productJpaRepository.getReferenceById(pricePolicy.getProduct().getId());
        PricePolicyJpaEntity saved = pricePolicyJpaRepository.save(PricePolicyJpaEntity.from(productRef, pricePolicy));
        return saved.getId();
    }

    @Override
    public void deleteById(Long pricePolicyId) {
        productOptionPricePolicyJpaRepository.deleteByPricePolicyId(pricePolicyId);
        pricePolicyJpaRepository.deleteById(pricePolicyId);
    }

    @Override
    public boolean existsByIdAndProductId(Long pricePolicyId, Long productId) {
        return pricePolicyJpaRepository.existsByIdAndProductJpaEntity_Id(pricePolicyId, productId);
    }

    @Override
    public Optional<PricePolicy> findByProductAndOptionIds(Long productId, List<Long> optionIds) {
        if (optionIds == null || optionIds.isEmpty()) {
            return Optional.empty();
        }

        List<Long> candidateIds = productOptionPricePolicyJpaRepository.findCandidatePricePolicyIds(optionIds, optionIds.size());
        if (candidateIds == null || candidateIds.isEmpty()) {
            return Optional.empty();
        }

        Long matchedId = null;
        for (Long id : candidateIds) {
            long mappingCount = productOptionPricePolicyJpaRepository.countByPricePolicyJpaEntity_Id(id);
            if (mappingCount == optionIds.size()) {
                // ensure policy belongs to product
                var policyOpt = pricePolicyJpaRepository.findById(id);
                if (policyOpt.isPresent() && policyOpt.get().getProductJpaEntity().getId().equals(productId)) {
                    if (matchedId == null || id > matchedId) {
                        matchedId = id; // pick latest if multiple
                    }
                }
            }
        }

        if (matchedId == null) {
            return Optional.empty();
        }

        PricePolicyJpaEntity entity = pricePolicyJpaRepository.findById(matchedId).orElse(null);
        if (entity == null) {
            return Optional.empty();
        }

        ProductJpaEntity p = entity.getProductJpaEntity();
        var productDomain = com.personal.marketnote.product.domain.product.Product.of(
                p.getId(),
                p.getSellerId(),
                p.getName(),
                p.getBrandName(),
                p.getDetail(),
                p.getPrice(),
                p.getDiscountPrice(),
                p.getDiscountRate(),
                p.getAccumulatedPoint(),
                p.getSales(),
                p.getViewCount(),
                p.getPopularity(),
                p.isFindAllOptionsYn(),
                java.util.List.of(),
                p.getOrderNum(),
                p.getStatus()
        );

        PricePolicy domain = com.personal.marketnote.product.domain.product.PricePolicy.of(
                productDomain,
                entity.getPrice(),
                entity.getDiscountPrice(),
                entity.getAccumulationRate(),
                entity.getAccumulatedPoint(),
                entity.getDiscountRate()
        );
        return Optional.of(domain);
    }
}
