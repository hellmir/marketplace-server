package com.personal.marketnote.product.adapter.out.persistence.productoption.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseOrderedEntity;
import com.personal.marketnote.product.domain.product.ProductOption;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_option")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductOptionJpaEntity extends BaseOrderedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_option_product_option_category"))
    private ProductOptionCategoryJpaEntity productOptionCategoryJpaEntity;

    @Column(name = "content", nullable = false, length = 511)
    private String content;

    @Column(name = "price")
    private Long price;

    @Column(name = "accumulated_point")
    private Long accumulatedPoint;

    public static ProductOptionJpaEntity of(ProductOptionCategoryJpaEntity productOptionCategoryJpaEntity, String content, Long price, Long accumulatedPoint) {
        return ProductOptionJpaEntity.builder()
                .productOptionCategoryJpaEntity(productOptionCategoryJpaEntity)
                .content(content)
                .price(price)
                .accumulatedPoint(accumulatedPoint)
                .build();
    }

    public static ProductOptionJpaEntity from(
            ProductOptionCategoryJpaEntity productOptionCategoryJpaEntity, ProductOption option
    ) {
        return ProductOptionJpaEntity.builder()
                .productOptionCategoryJpaEntity(productOptionCategoryJpaEntity)
                .content(option.getContent())
                .price(option.getPrice())
                .accumulatedPoint(option.getAccumulatedPoint())
                .build();
    }
}


