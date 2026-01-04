package com.personal.marketnote.product.adapter.out.persistence.productcategory;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.persistence.productcategory.entity.ProductCategoryJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productcategory.repository.ProductCategoryJpaRepository;
import com.personal.marketnote.product.port.out.productcategory.FindProductCategoryPort;
import com.personal.marketnote.product.port.out.productcategory.ReplaceProductCategoriesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductCategoryPersistenceAdapter implements ReplaceProductCategoriesPort, FindProductCategoryPort {
    private final ProductCategoryJpaRepository productCategoryJpaRepository;

    @Override
    @Transactional(isolation = READ_COMMITTED)
    @Caching(evict = {
            @CacheEvict(value = "product:detail", key = "#productId"),
            @CacheEvict(value = "product:list:first", allEntries = true)
    })
    public void replaceProductCategories(Long productId, List<Long> categoryIds) {
        productCategoryJpaRepository.deleteByProductId(productId);
        if (categoryIds == null || categoryIds.isEmpty()) {
            return;
        }

        List<ProductCategoryJpaEntity> toSave = categoryIds.stream()
                .map(categoryId -> ProductCategoryJpaEntity.of(productId, categoryId))
                .toList();

        productCategoryJpaRepository.saveAll(toSave);
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return productCategoryJpaRepository.existsByCategoryId(categoryId);
    }
}
