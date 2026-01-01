package com.personal.marketnote.product.adapter.out.persistence.product.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseOrderedEntity;
import com.personal.marketnote.product.domain.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductJpaEntity extends BaseOrderedEntity {
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "brand_name", length = 255)
    private String brandName;

    @Column(name = "detail", length = 1023)
    private String detail;

    @Column(name = "sales", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer sales;

    public static ProductJpaEntity from(Product product) {
        return ProductJpaEntity.builder()
                .sellerId(product.getSellerId())
                .name(product.getName())
                .brandName(product.getBrandName())
                .detail(product.getDetail())
                .sales(product.getSales())
                .build();
    }
}


