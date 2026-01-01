package com.personal.marketnote.product.adapter.out.persistence.productoption.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseOrderedEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.domain.product.ProductOptionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_option_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductOptionCategoryJpaEntity extends BaseOrderedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductJpaEntity productJpaEntity;

    @OneToMany(mappedBy = "productOptionCategoryJpaEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionJpaEntity> productOptionJpaEntities = new ArrayList<>();

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    public static ProductOptionCategoryJpaEntity from(ProductOptionCategory category, ProductJpaEntity productJpaEntity) {
        ProductOptionCategoryJpaEntity productOptionCategoryJpaEntity = ProductOptionCategoryJpaEntity.builder()
                .productJpaEntity(productJpaEntity)
                .name(category.getName())
                .build();

        List<ProductOptionJpaEntity> productOptionJpaEntities = category.getOptions()
                .stream()
                .map(option -> ProductOptionJpaEntity.from(productOptionCategoryJpaEntity, option))
                .toList();
        productOptionCategoryJpaEntity.productOptionJpaEntities = productOptionJpaEntities;

        return productOptionCategoryJpaEntity;
    }

    public void addOrderNum() {
        setIdToOrderNum();
        productOptionJpaEntities.forEach(ProductOptionJpaEntity::setIdToOrderNum);
    }
}
