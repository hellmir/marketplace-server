package com.personal.marketnote.product.adapter.out.persistence.pricepolicy;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.mapper.PricePolicyJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.repository.ProductJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionPricePolicyJpaRepository;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import com.personal.marketnote.product.port.out.pricepolicy.DeletePricePolicyPort;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePoliciesPort;
import com.personal.marketnote.product.port.out.pricepolicy.FindPricePolicyPort;
import com.personal.marketnote.product.port.out.pricepolicy.SavePricePolicyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class PricePolicyPersistenceAdapter implements SavePricePolicyPort, FindPricePolicyPort, FindPricePoliciesPort, DeletePricePolicyPort {
    private final ProductJpaRepository productJpaRepository;
    private final PricePolicyJpaRepository pricePolicyJpaRepository;
    private final ProductOptionPricePolicyJpaRepository productOptionPricePolicyJpaRepository;

    @Override
    @CacheEvict(value = {"product:detail", "product:price-policy"}, key = "#pricePolicy.product.id", beforeInvocation = false)
    public Long save(PricePolicy pricePolicy) {
        ProductJpaEntity productRef = productJpaRepository.getReferenceById(pricePolicy.getProduct().getId());
        PricePolicyJpaEntity savedPricePolicyJpaEntity
                = pricePolicyJpaRepository.save(PricePolicyJpaEntity.from(productRef, pricePolicy));
        savedPricePolicyJpaEntity.setIdToOrderNum();

        return savedPricePolicyJpaEntity.getId();
    }

    @Override
    public Optional<PricePolicy> findById(Long id) {
        return PricePolicyJpaEntityToDomainMapper.mapToDomain(pricePolicyJpaRepository.findById(id).orElse(null));
    }

    @Override
    public Optional<PricePolicy> findByProductAndOptionIds(Long productId, List<Long> optionIds) {
        if (!FormatValidator.hasValue(optionIds)) {
            return Optional.empty();
        }

        List<Long> candidateIds = productOptionPricePolicyJpaRepository.findCandidatePricePolicyIds(optionIds, optionIds.size());
        if (!FormatValidator.hasValue(candidateIds)) {
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

        ProductJpaEntity productJpaEntity = entity.getProductJpaEntity();
        Product product = Product.of(
                productJpaEntity.getId(),
                productJpaEntity.getSellerId(),
                productJpaEntity.getName(),
                productJpaEntity.getBrandName(),
                productJpaEntity.getDetail(),
                PricePolicyJpaEntityToDomainMapper.mapToDomain(productJpaEntity.getDefaultPricePolicy())
                        .orElse(null),
                productJpaEntity.getSales(),
                productJpaEntity.getViewCount(),
                productJpaEntity.getPopularity(),
                productJpaEntity.isFindAllOptionsYn(),
                java.util.List.of(),
                productJpaEntity.getOrderNum(),
                productJpaEntity.getStatus()
        );

        PricePolicy pricePolicy = PricePolicy.of(
                entity.getId(),
                product,
                entity.getPrice(),
                entity.getDiscountPrice(),
                entity.getAccumulationRate(),
                entity.getAccumulatedPoint(),
                entity.getDiscountRate(),
                entity.getPopularity(),
                entity.getStatus(),
                entity.getOrderNum()
        );

        return Optional.of(pricePolicy);
    }

    @Override
    public Optional<PricePolicy> findByOptionIds(List<Long> optionIds) {
        return pricePolicyJpaRepository.findOneByOptionIds(optionIds)
                .map(PricePolicyJpaEntityToDomainMapper::mapToDomain)
                .orElse(null);
    }

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

    @Override
    public List<PricePolicy> findPricePolicies(
            List<Long> pricePolicyIds,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    ) {
        boolean isAsc = pageable.getSort()
                .stream()
                .findFirst()
                .map(Sort.Order::isAscending)
                .orElse(true);

        String pattern = generateSearchPattern(searchKeyword);

        List<PricePolicyJpaEntity> entities = isAsc
                ? pricePolicyJpaRepository.findAllActiveByCursorAsc(
                pricePolicyIds,
                cursor,
                pageable,
                sortProperty.getCamelCaseValue(),
                searchTarget.getCamelCaseValue(),
                pattern,
                null
        )
                : pricePolicyJpaRepository.findAllActiveByCursorDesc(
                pricePolicyIds,
                cursor,
                pageable,
                sortProperty.getCamelCaseValue(),
                searchTarget.getCamelCaseValue(),
                pattern,
                null
        );

        return entities.stream()
                .map(policyEntity -> PricePolicyJpaEntityToDomainMapper.mapToDomain(
                        policyEntity, productOptionPricePolicyJpaRepository.findOptionIdsByPricePolicyId(policyEntity.getId())
                ))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public List<PricePolicy> findPricePoliciesByCategoryId(
            Long categoryId,
            List<Long> pricePolicyIds,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    ) {
        boolean isAsc = pageable.getSort()
                .stream()
                .findFirst()
                .map(Sort.Order::isAscending)
                .orElse(true);

        String pattern = generateSearchPattern(searchKeyword);

        List<PricePolicyJpaEntity> entities = isAsc
                ? pricePolicyJpaRepository.findAllActiveByCursorAsc(
                pricePolicyIds,
                cursor,
                pageable,
                sortProperty.getCamelCaseValue(),
                searchTarget.getCamelCaseValue(),
                pattern,
                categoryId
        )
                : pricePolicyJpaRepository.findAllActiveByCursorDesc(
                pricePolicyIds,
                cursor,
                pageable,
                sortProperty.getCamelCaseValue(),
                searchTarget.getCamelCaseValue(),
                pattern,
                categoryId
        );

        return entities.stream()
                .map(policyEntity -> PricePolicyJpaEntityToDomainMapper.mapToDomain(
                        policyEntity, productOptionPricePolicyJpaRepository.findOptionIdsByPricePolicyId(policyEntity.getId())
                ))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private String generateSearchPattern(String searchKeyword) {
        if (FormatValidator.hasValue(searchKeyword)) {
            return "%" + searchKeyword + "%";
        }

        return null;
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
}
