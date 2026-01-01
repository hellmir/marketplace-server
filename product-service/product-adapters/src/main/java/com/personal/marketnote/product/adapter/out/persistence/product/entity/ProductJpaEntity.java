package com.personal.marketnote.product.adapter.out.persistence.product.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseOrderedGeneralEntity;
import com.personal.marketnote.product.domain.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductJpaEntity extends BaseOrderedGeneralEntity {
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "brand_name", length = 255)
    private String brandName;

    @Column(name = "detail", length = 1023)
    private String detail;

    // Latest current_price from price_policy_history (read-only)
    @Formula("(SELECT pp.current_price FROM price_policy_history pp WHERE pp.product_id = id ORDER BY pp.id DESC LIMIT 1)")
    private Long currentPrice;

    // Latest accumulated_point from price_policy_history (read-only)
    @Formula("(SELECT pp.accumulated_point FROM price_policy_history pp WHERE pp.product_id = id ORDER BY pp.id DESC LIMIT 1)")
    private Long accumulatedPoint;

    @Column(name = "sales", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer sales;

    @Column(name = "view_count", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long viewCount;

    @Column(name = "popularity", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long popularity;

    public static ProductJpaEntity from(Product product) {
        return ProductJpaEntity.builder()
                .sellerId(product.getSellerId())
                .name(product.getName())
                .brandName(product.getBrandName())
                .detail(product.getDetail())
                .sales(product.getSales())
                .viewCount(product.getViewCount())
                .popularity(product.getPopularity())
                .build();
    }
}
