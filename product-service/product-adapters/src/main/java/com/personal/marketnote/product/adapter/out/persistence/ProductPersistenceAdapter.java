package com.personal.marketnote.product.adapter.out.persistence;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.product.adapter.out.mapper.ProductJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.repository.ProductJpaRepository;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.product.FindProductsPort;
import com.personal.marketnote.product.port.out.product.SaveProductPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements SaveProductPort, FindProductPort, FindProductsPort {
    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        ProductJpaEntity savedEntity = productJpaRepository.save(ProductJpaEntity.from(product));
        savedEntity.setIdToOrderNum();

        return ProductJpaEntityToDomainMapper.mapToDomain(savedEntity).get();
    }

    @Override
    public boolean existsByIdAndSellerId(Long productId, Long sellerId) {
        return productJpaRepository.existsByIdAndSellerId(productId, sellerId);
    }

    @Override
    public java.util.Optional<Product> findById(Long productId) {
        return ProductJpaEntityToDomainMapper.mapToDomain(
                productJpaRepository.findById(productId).orElse(null));
    }

    @Override
    public java.util.List<com.personal.marketnote.product.domain.product.Product> findAllActive() {
        return productJpaRepository.findAllByStatusOrderByOrderNumAsc(
                        com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE).stream()
                .map(entity -> ProductJpaEntityToDomainMapper.mapToDomain(entity).get())
                .toList();
    }

    @Override
    public java.util.List<com.personal.marketnote.product.domain.product.Product> findAllActiveByCategoryId(Long categoryId) {
        return productJpaRepository.findAllActiveByCategoryId(categoryId).stream()
                .map(entity -> ProductJpaEntityToDomainMapper.mapToDomain(entity).get())
                .toList();
    }
}
