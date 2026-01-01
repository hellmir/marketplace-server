package com.personal.marketnote.product.adapter.out.persistence.product.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseOrderedGeneralEntity;
import com.personal.marketnote.product.domain.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@DynamicInsert
@DynamicUpdate
public class ProductJpaEntity extends BaseOrderedGeneralEntity {
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "brand_name", length = 255)
    private String brandName;

    @Column(name = "detail", length = 1023)
    private String detail;

    @Formula("(SELECT pp.price FROM price_policy_history pp WHERE pp.product_id = id ORDER BY pp.id DESC LIMIT 1)")
    private Long price;

    @Formula("(SELECT pp.discount_price FROM price_policy_history pp WHERE pp.product_id = id ORDER BY pp.id DESC LIMIT 1)")
    private Long discountPrice;

    @Formula("(SELECT pp.discount_rate FROM price_policy_history pp WHERE pp.product_id = id ORDER BY pp.id DESC LIMIT 1)")
    private BigDecimal discountRate;

    @Formula("(SELECT pp.accumulated_point FROM price_policy_history pp WHERE pp.product_id = id ORDER BY pp.id DESC LIMIT 1)")
    private Long accumulatedPoint;

    @Column(name = "sales", nullable = false, insertable = false, columnDefinition = "INT DEFAULT 0")
    private Integer sales;

    @Column(name = "view_count", nullable = false, insertable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long viewCount;

    @Column(name = "popularity", nullable = false, insertable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long popularity;

    @Column(name = "find_all_options_yn", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean findAllOptionsYn;

    public static ProductJpaEntity from(Product product) {
        return ProductJpaEntity.builder()
                .sellerId(product.getSellerId())
                .name(product.getName())
                .brandName(product.getBrandName())
                .detail(product.getDetail())
                .findAllOptionsYn(product.isFindAllOptionsYn())
                .build();
    }

    public void updateFrom(Product product) {
        this.name = product.getName();
        this.brandName = product.getBrandName();
        this.detail = product.getDetail();
        this.findAllOptionsYn = product.isFindAllOptionsYn();
    }
}
