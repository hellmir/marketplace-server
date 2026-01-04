package com.personal.marketnote.product.adapter.out.persistence.pricepolicy;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.repository.ProductJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionPricePolicyJpaRepository;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.port.out.pricepolicy.DeletePricePolicyPort;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePolicyPort;
import com.personal.marketnote.product.port.out.pricepolicy.SavePricePolicyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class PricePolicyPersistenceAdapter implements SavePricePolicyPort, DeletePricePolicyPort, FindPricePolicyPort {
    private final ProductJpaRepository productJpaRepository;
    private final PricePolicyJpaRepository pricePolicyJpaRepository;
    private final ProductOptionPricePolicyJpaRepository productOptionPricePolicyJpaRepository;

    @Override
    @CacheEvict(value = {"product:detail", "product:price-policy"}, key = "#pricePolicy.product.id", beforeInvocation = false)
    public Long save(PricePolicy pricePolicy) {
        ProductJpaEntity productRef = productJpaRepository.getReferenceById(pricePolicy.getProduct().getId());
        com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity saved = pricePolicyJpaRepository.save(com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity.from(productRef, pricePolicy));
        return saved.getId();
    }

    @Override
    public void deleteById(Long pricePolicyId) {
        Long productId = pricePolicyJpaRepository.findById(pricePolicyId)
                .map(entity -> entity.getProductJpaEntity().getId())
                .orElse(null);
        deleteByIdInternal(productId, pricePolicyId);
    }

    @CacheEvict(value = {"product:detail", "product:price-policy"}, key = "#productId", beforeInvocation = false)
    public void deleteByIdInternal(Long productId, Long pricePolicyId) {
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
                Optional<PricePolicyJpaEntity> policyOpt = pricePolicyJpaRepository.findById(id);

                if (policyOpt.isPresent() && policyOpt.get().getProductJpaEntity().getId().equals(productId)) {
                    if (!FormatValidator.hasValue(matchedId) || id > matchedId) {
                        matchedId = id;
                    }
                }
            }
        }

        if (!FormatValidator.hasValue(matchedId)) {
            return Optional.empty();
        }

        PricePolicyJpaEntity entity = pricePolicyJpaRepository.findById(matchedId)
                .orElse(null);

        if (!FormatValidator.hasValue(entity)) {
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

        PricePolicy domain = PricePolicy.of(
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
