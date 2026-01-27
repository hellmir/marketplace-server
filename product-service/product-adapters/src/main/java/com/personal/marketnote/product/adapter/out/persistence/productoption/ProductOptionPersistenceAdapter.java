package com.personal.marketnote.product.adapter.out.persistence.productoption;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.mapper.ProductJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.repository.ProductJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionCategoryJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionPricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionCategoryJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionPricePolicyJpaRepository;
import com.personal.marketnote.product.domain.option.ProductOptionCategory;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicy;
import com.personal.marketnote.product.domain.pricepolicy.PricePolicyCreateState;
import com.personal.marketnote.product.exception.ProductNotFoundException;
import com.personal.marketnote.product.port.out.productoption.DeleteProductOptionCategoryPort;
import com.personal.marketnote.product.port.out.productoption.FindProductOptionCategoryPort;
import com.personal.marketnote.product.port.out.productoption.SaveProductOptionsPort;
import com.personal.marketnote.product.port.out.productoption.UpdateOptionPricePolicyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductOptionPersistenceAdapter implements SaveProductOptionsPort, DeleteProductOptionCategoryPort, FindProductOptionCategoryPort, UpdateOptionPricePolicyPort {
    private final ProductJpaRepository productJpaRepository;
    private final ProductOptionCategoryJpaRepository productOptionCategoryJpaRepository;
    private final ProductOptionJpaRepository productOptionJpaRepository;
    private final ProductOptionPricePolicyJpaRepository productOptionPricePolicyJpaRepository;
    private final PricePolicyJpaRepository pricePolicyJpaRepository;

    @Override
    @CacheEvict(value = "product:detail", key = "#productOptionCategory.product.id")
    public ProductOptionCategory save(ProductOptionCategory productOptionCategory) {
        Long productId = productOptionCategory.getProduct().getId();
        ProductJpaEntity product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        ProductOptionCategoryJpaEntity savedCategory = productOptionCategoryJpaRepository.save(
                ProductOptionCategoryJpaEntity.from(productOptionCategory, product));
        savedCategory.addOrderNum();

        // 생성된 각 옵션 및 기존 옵션과의 조합에 대해 기본 가격 정책 등록
        PricePolicyJpaEntity defaultPricePolicy = product.getDefaultPricePolicy();

        if (FormatValidator.hasNoValue(defaultPricePolicy)) {
            return ProductJpaEntityToDomainMapper.mapToDomain(savedCategory).orElse(null);
        }

        // 상품의 모든 활성 카테고리와 옵션 가져오기
        List<ProductOptionCategoryJpaEntity> allCategories = productOptionCategoryJpaRepository
                .findActiveWithOptionsByProductId(productId);

        // 1. 새로 생성된 카테고리의 각 옵션에 대해 가격 정책 생성
        List<PricePolicyJpaEntity> newPolicies = new ArrayList<>();
        List<ProductOptionPricePolicyJpaEntity> newOptionPolicies = new ArrayList<>();
        savedCategory.getProductOptionJpaEntities()
                .forEach(newOption -> {
                    PricePolicyJpaEntity pricePolicy = createPricePolicyFromDefault(product, defaultPricePolicy);
                    newPolicies.add(pricePolicy);
                    newOptionPolicies.add(ProductOptionPricePolicyJpaEntity.of(newOption, pricePolicy));
                });

        // 2. 모든 카테고리의 옵션 조합에 대해 가격 정책 생성
        if (allCategories.size() > 1) {
            List<List<ProductOptionJpaEntity>> optionGroups = allCategories.stream()
                    .map(ProductOptionCategoryJpaEntity::getProductOptionJpaEntities)
                    .filter(FormatValidator::hasValue)
                    .toList();

            if (FormatValidator.hasValue(optionGroups) && optionGroups.size() > 1) {
                List<List<ProductOptionJpaEntity>> productOptionCombinations = cartesianProduct(optionGroups);
                productOptionCombinations.forEach(productOptionCombination -> {
                    PricePolicyJpaEntity pricePolicy = createPricePolicyFromDefault(product, defaultPricePolicy);
                    newPolicies.add(pricePolicy);

                    // 조합의 모든 옵션과 가격 정책 연결
                    productOptionCombination.forEach(
                            option -> newOptionPolicies.add(
                                    ProductOptionPricePolicyJpaEntity.of(option, pricePolicy)
                            )
                    );
                });
            }
        }

        List<PricePolicyJpaEntity> savedPricePolicyJpaEntities = pricePolicyJpaRepository.saveAll(newPolicies);
        savedPricePolicyJpaEntities.forEach(PricePolicyJpaEntity::setIdToOrderNum);
        productOptionPricePolicyJpaRepository.saveAll(newOptionPolicies);

        return ProductJpaEntityToDomainMapper.mapToDomain(savedCategory).orElse(null);
    }

    private PricePolicyJpaEntity createPricePolicyFromDefault(
            ProductJpaEntity product,
            PricePolicyJpaEntity defaultPricePolicy
    ) {
        PricePolicy pricePolicy = PricePolicy.from(
                PricePolicyCreateState.builder()
                        .price(defaultPricePolicy.getPrice())
                        .discountPrice(defaultPricePolicy.getDiscountPrice())
                        .discountRate(defaultPricePolicy.getDiscountRate())
                        .accumulatedPoint(defaultPricePolicy.getAccumulatedPoint())
                        .accumulationRate(defaultPricePolicy.getAccumulationRate())
                        .build()
        );

        return PricePolicyJpaEntity.from(product, pricePolicy);
    }

    private List<List<ProductOptionJpaEntity>> cartesianProduct(
            List<List<ProductOptionJpaEntity>> optionGroups
    ) {
        List<List<ProductOptionJpaEntity>> result = new ArrayList<>();
        if (FormatValidator.hasNoValue(optionGroups)) {
            return result;
        }

        backtrackCartesian(optionGroups, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrackCartesian(
            List<List<ProductOptionJpaEntity>> optionGroups,
            int depth,
            List<ProductOptionJpaEntity> path,
            List<List<ProductOptionJpaEntity>> result
    ) {
        if (depth == optionGroups.size()) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (ProductOptionJpaEntity option : optionGroups.get(depth)) {
            path.add(option);
            backtrackCartesian(optionGroups, depth + 1, path, result);
            path.removeLast();
        }
    }

    @Override
    public void deleteById(Long id) {
        Long productId = productOptionCategoryJpaRepository.findProductIdByCategoryId(id);
        deleteByIdInternal(productId, id);
    }

    @CacheEvict(value = "product:detail", key = "#productId")
    public void deleteByIdInternal(Long productId, Long id) {
        List<Long> optionIds = productOptionJpaRepository.findIdsByCategoryId(id);
        if (FormatValidator.hasValue(optionIds)) {
            productOptionPricePolicyJpaRepository.deleteByOptionIds(optionIds);
            productOptionJpaRepository.deleteAllByCategoryId(id);
        }

        productOptionCategoryJpaRepository.deleteById(id);
    }

    @Override
    public List<ProductOptionCategory> findActiveWithOptionsByProductId(Long productId) {
        return productOptionCategoryJpaRepository.findActiveWithOptionsByProductId(productId)
                .stream()
                .map(ProductJpaEntityToDomainMapper::mapToDomain)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    @CacheEvict(value = "product:detail", key = "#productId")
    public void assignPricePolicyToOptions(Long pricePolicyId, List<Long> optionIds) {
        // 기존 동일 옵션 조합 가격 정책 삭제
        List<PricePolicyJpaEntity> existentPricePolicies = pricePolicyJpaRepository.findByOptionIds(optionIds);
        existentPricePolicies.forEach(PricePolicyJpaEntity::deactivate);

        PricePolicyJpaEntity pricePolicyRef = pricePolicyJpaRepository.getReferenceById(pricePolicyId);
        for (Long optionId : optionIds) {
            ProductOptionJpaEntity optionRef = productOptionJpaRepository.getReferenceById(optionId);
            ProductOptionPricePolicyJpaEntity optionPricePolicy = ProductOptionPricePolicyJpaEntity.of(optionRef, pricePolicyRef);
            productOptionPricePolicyJpaRepository.save(optionPricePolicy);
        }
    }
}
