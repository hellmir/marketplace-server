package com.personal.marketnote.product.adapter.out.persistence;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.persistence.productcategory.entity.ProductCategoryJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.productcategory.repository.ProductCategoryJpaRepository;
import com.personal.marketnote.product.port.out.productcategory.ReplaceProductCategoriesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductCategoryPersistenceAdapter implements ReplaceProductCategoriesPort {
    private final ProductCategoryJpaRepository productCategoryJpaRepository;

    @Override
    @Transactional(isolation = READ_UNCOMMITTED)
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
}
