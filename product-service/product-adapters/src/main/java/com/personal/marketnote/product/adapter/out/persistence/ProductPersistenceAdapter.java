package com.personal.marketnote.product.adapter.out.persistence;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.adapter.out.mapper.ProductJpaEntityToDomainMapper;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaGeneralEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.repository.ProductJpaRepository;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductSearchTarget;
import com.personal.marketnote.product.domain.product.ProductSortProperty;
import com.personal.marketnote.product.port.out.product.FindProductPort;
import com.personal.marketnote.product.port.out.product.SaveProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements SaveProductPort, FindProductPort {
    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        ProductJpaGeneralEntity savedEntity = productJpaRepository.save(ProductJpaGeneralEntity.from(product));
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
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream()
                .map(entity -> ProductJpaEntityToDomainMapper.mapToDomain(entity).get())
                .toList();
    }

    @Override
    public List<Product> findAllByCategoryId(Long categoryId) {
        return productJpaRepository.findAllByCategoryIdOrderByOrderNumAsc(categoryId).stream()
                .map(entity -> ProductJpaEntityToDomainMapper.mapToDomain(entity).get())
                .toList();
    }

    @Override
    public List<Product> findAllActive(
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    ) {
        boolean isAsc = isAsc(pageable);
        String searchPattern = generateSearchPattern(searchKeyword);

        List<ProductJpaGeneralEntity> entities = findEntities(
                isAsc,
                cursor,
                pageable,
                sortProperty,
                searchTarget,
                searchPattern
        );

        return entities.stream()
                .map(entity -> ProductJpaEntityToDomainMapper.mapToDomain(entity).orElse(null))
                .toList();
    }

    @Override
    public List<Product> findAllActiveByCategoryId(
            Long categoryId,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchKeyword
    ) {
        boolean isAsc = isAsc(pageable);
        String searchPattern = generateSearchPattern(searchKeyword);

        List<ProductJpaGeneralEntity> entities = findCategorizedEntities(
                isAsc,
                categoryId,
                cursor,
                pageable,
                sortProperty,
                searchTarget,
                searchPattern
        );

        return entities.stream()
                .map(entity -> ProductJpaEntityToDomainMapper.mapToDomain(entity).orElse(null))
                .toList();
    }

    private boolean isAsc(Pageable pageable) {
        return pageable.getSort()
                .stream()
                .findFirst()
                .map(Sort.Order::isAscending)
                .orElse(true);
    }

    private List<ProductJpaGeneralEntity> findEntities(
            boolean isAsc,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchPattern
    ) {
        if (isAsc) {
            return productJpaRepository.findAllActiveByCursorAsc(
                    cursor,
                    pageable,
                    sortProperty.getCamelCaseValue(),
                    searchTarget.getCamelCaseValue(),
                    searchPattern
            );
        }

        return productJpaRepository.findAllActiveByCursorDesc(
                cursor,
                pageable,
                sortProperty.getCamelCaseValue(),
                searchTarget.getCamelCaseValue(),
                searchPattern
        );
    }

    private List<ProductJpaGeneralEntity> findCategorizedEntities(
            boolean isAsc,
            Long categoryId,
            Long cursor,
            Pageable pageable,
            ProductSortProperty sortProperty,
            ProductSearchTarget searchTarget,
            String searchPattern
    ) {
        if (isAsc) {
            return productJpaRepository.findAllActiveByCategoryIdCursorAsc(
                    categoryId,
                    cursor,
                    pageable,
                    sortProperty.getCamelCaseValue(),
                    searchTarget.getCamelCaseValue(),
                    searchPattern
            );
        }

        return productJpaRepository.findAllActiveByCategoryIdCursorDesc(
                categoryId,
                cursor,
                pageable,
                sortProperty.getCamelCaseValue(),
                searchTarget.getCamelCaseValue(),
                searchPattern
        );
    }

    @Override
    public long countActive(ProductSearchTarget searchTarget, String searchKeyword) {
        String searchPattern = generateSearchPattern(searchKeyword);

        return productJpaRepository.countActive(
                searchTarget.getCamelCaseValue(),
                searchPattern
        );
    }

    @Override
    public long countActiveByCategoryId(Long categoryId, ProductSearchTarget searchTarget, String searchKeyword) {
        String searchPattern = generateSearchPattern(searchKeyword);

        return productJpaRepository.countActiveByCategoryId(
                categoryId,
                searchTarget.getCamelCaseValue(),
                searchPattern
        );
    }

    private String generateSearchPattern(String searchKeyword) {
        if (FormatValidator.hasValue(searchKeyword)) {
            return "%" + searchKeyword + "%";
        }

        return null;
    }
}
