package com.personal.marketnote.product.adapter.out.persistence;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.mapper.ProductJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.repository.ProductJpaRepository;
import com.personal.marketnote.product.adapter.out.persistence.productoption.entity.ProductOptionCategoryJpaGeneralEntity;
import com.personal.marketnote.product.adapter.out.persistence.productoption.repository.ProductOptionCategoryJpaRepository;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;
import com.personal.marketnote.product.port.out.productoption.DeleteProductOptionCategoryPort;
import com.personal.marketnote.product.port.out.productoption.SaveProductOptionsPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductOptionPersistenceAdapter implements SaveProductOptionsPort, DeleteProductOptionCategoryPort {
    private final ProductJpaRepository productJpaRepository;
    private final ProductOptionCategoryJpaRepository productOptionCategoryJpaRepository;

    @Override
    public ProductOptionCategory save(ProductOptionCategory productOptionCategory) {
        ProductJpaEntity productRef = productJpaRepository.getReferenceById(
                productOptionCategory.getProduct().getId());

        ProductOptionCategoryJpaGeneralEntity savedCategory = productOptionCategoryJpaRepository.save(
                ProductOptionCategoryJpaGeneralEntity.from(productOptionCategory, productRef));
        savedCategory.addOrderNum();

        return ProductJpaEntityToDomainMapper.mapToDomain(savedCategory).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        productOptionCategoryJpaRepository.deleteById(id);
    }
}
