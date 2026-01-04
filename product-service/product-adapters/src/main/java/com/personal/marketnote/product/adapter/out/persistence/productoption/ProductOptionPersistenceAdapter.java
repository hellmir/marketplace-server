package com.personal.marketnote.product.adapter.out.persistence.productoption;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.mapper.ProductJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity.PricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.repository.ProductJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionCategoryJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionPricePolicyJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionCategoryJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionPricePolicyJpaRepository;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;
import com.personal.marketnote.product.port.out.productoption.DeleteProductOptionCategoryPort;
import com.personal.marketnote.product.port.out.productoption.FindProductOptionCategoryPort;
import com.personal.marketnote.product.port.out.productoption.SaveProductOptionsPort;
import com.personal.marketnote.product.port.out.productoption.UpdateOptionPricePolicyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductOptionPersistenceAdapter implements SaveProductOptionsPort, DeleteProductOptionCategoryPort, FindProductOptionCategoryPort, UpdateOptionPricePolicyPort {
    private final ProductJpaRepository productJpaRepository;
    private final ProductOptionCategoryJpaRepository productOptionCategoryJpaRepository;
    private final ProductOptionJpaRepository productOptionJpaRepository;
    private final ProductOptionPricePolicyJpaRepository productOptionPricePolicyJpaRepository;
    private final com.personal.marketnote.product.adapter.out.persistence.pricepolicy.repository.PricePolicyJpaRepository pricePolicyJpaRepository;

    @Override
    @CacheEvict(value = "product:detail", key = "#productOptionCategory.product.id")
    public ProductOptionCategory save(ProductOptionCategory productOptionCategory) {
        ProductJpaEntity productRef = productJpaRepository.getReferenceById(
                productOptionCategory.getProduct().getId());

        ProductOptionCategoryJpaEntity savedCategory = productOptionCategoryJpaRepository.save(
                ProductOptionCategoryJpaEntity.from(productOptionCategory, productRef));
        savedCategory.addOrderNum();

        return ProductJpaEntityToDomainMapper.mapToDomain(savedCategory).orElse(null);
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
    public void assignPricePolicyToOptions(Long pricePolicyId, List<Long> optionIds) {
        ProductJpaEntity productOfPolicy = pricePolicyJpaRepository.getReferenceById(pricePolicyId).getProductJpaEntity();
        assignPricePolicyToOptionsInternal(productOfPolicy.getId(), pricePolicyId, optionIds);
    }

    @CacheEvict(value = "product:detail", key = "#productId")
    public void assignPricePolicyToOptionsInternal(Long productId, Long pricePolicyId, List<Long> optionIds) {
        PricePolicyJpaEntity pricePolicyRef = pricePolicyJpaRepository.getReferenceById(pricePolicyId);
        for (Long optionId : optionIds) {
            var optionRef = productOptionJpaRepository.getReferenceById(optionId);
            var mapping = ProductOptionPricePolicyJpaEntity.of(optionRef, pricePolicyRef);
            productOptionPricePolicyJpaRepository.save(mapping);
        }
    }
}
