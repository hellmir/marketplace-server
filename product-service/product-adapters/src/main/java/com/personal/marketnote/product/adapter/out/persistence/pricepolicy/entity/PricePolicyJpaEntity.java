package com.personal.marketnote.product.adapter.out.persistence.pricepolicy.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseGeneralEntity;
import com.personal.marketnote.product.adapter.out.persistence.product.entity.ProductJpaEntity;
import com.personal.marketnote.product.domain.product.PricePolicy;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "price_policy_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class PricePolicyJpaEntity extends BaseGeneralEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_price_policy_product"))
    private ProductJpaEntity productJpaEntity;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "current_price", nullable = false)
    private Long currentPrice;

    // start_at, end_at removed per latest spec

    @Column(name = "accumulation_rate", nullable = false, precision = 3, scale = 1)
    private BigDecimal accumulationRate;

    @Column(name = "accumulated_point", nullable = false)
    private Long accumulatedPoint;

    @Column(name = "discount_rate", nullable = false, precision = 3, scale = 1)
    private BigDecimal discountRate;

    public static PricePolicyJpaEntity from(ProductJpaEntity productRef, PricePolicy pricePolicy) {
        return PricePolicyJpaEntity.builder()
                .productJpaEntity(productRef)
                .price(pricePolicy.getPrice())
                .currentPrice(pricePolicy.getCurrentPrice())
                .accumulationRate(pricePolicy.getAccumulationRate())
                .accumulatedPoint(pricePolicy.getAccumulatedPoint())
                .discountRate(pricePolicy.getDiscountRate())
                .build();
    }
}


