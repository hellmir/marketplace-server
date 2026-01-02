package com.personal.marketnote.product.adapter.out.persistence.product.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseOrderedGeneralEntity;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.domain.product.ProductTag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

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

    @Formula("""
            (
            SELECT pp.price
            FROM price_policy pp
            WHERE 1 = 1
            AND pp.status = 'ACTIVE'
            AND pp.product_id = id
            ORDER BY pp.id DESC LIMIT 1
            )
            """)
    private Long price;

    @Formula("""
            (
            SELECT pp.discount_price
            FROM price_policy pp
            WHERE 1 = 1
            AND pp.status = 'ACTIVE'
            AND pp.product_id = id
            ORDER BY pp.id DESC LIMIT 1
            )
            """)
    private Long discountPrice;

    @Formula("""
            (
            SELECT pp.discount_rate
            FROM price_policy pp
            WHERE 1 = 1
            AND pp.status = 'ACTIVE'
            AND pp.product_id = id
            ORDER BY pp.id DESC LIMIT 1
            )
            """)
    private BigDecimal discountRate;

    @Formula("""
            (
            SELECT pp.accumulated_point
            FROM price_policy pp
            WHERE 1 = 1
            AND pp.status = 'ACTIVE'
            AND pp.product_id = id
            ORDER BY pp.id DESC LIMIT 1
            )
            """)
    private Long accumulatedPoint;

    @Column(name = "sales", nullable = false, insertable = false, columnDefinition = "INT DEFAULT 0")
    private Integer sales;

    @Column(name = "view_count", nullable = false, insertable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long viewCount;

    @Column(name = "popularity", nullable = false, insertable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long popularity;

    @Column(name = "find_all_options_yn", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean findAllOptionsYn;

    @OneToMany(mappedBy = "productJpaEntity", cascade = {PERSIST, MERGE}, orphanRemoval = true)
    private List<ProductTagJpaEntity> productTagJpaEntities = new ArrayList<>();

    public static ProductJpaEntity from(Product product) {
        ProductJpaEntity productJpaEntity = ProductJpaEntity.builder()
                .sellerId(product.getSellerId())
                .name(product.getName())
                .brandName(product.getBrandName())
                .detail(product.getDetail())
                .findAllOptionsYn(product.isFindAllOptionsYn())
                .build();

        productJpaEntity.productTagJpaEntities = (
                product.getProductTags()
                        .stream()
                        .map(tag -> ProductTagJpaEntity.from(productJpaEntity, tag))
                        .collect(Collectors.toList())
        );

        return productJpaEntity;
    }

    public void updateFrom(Product product) {
        updateActivation(product);
        name = product.getName();
        brandName = product.getBrandName();
        detail = product.getDetail();
        findAllOptionsYn = product.isFindAllOptionsYn();

        productTagJpaEntities.clear();
        if (FormatValidator.hasValue(product.getProductTags())) {
            for (ProductTag tag : product.getProductTags()) {
                productTagJpaEntities.add(ProductTagJpaEntity.from(this, tag));
            }
        }
    }

    private void updateActivation(Product product) {
        if (product.isActive()) {
            activate();
            return;
        }

        if (product.isInactive()) {
            deactivate();
            return;
        }

        hide();
    }
}
