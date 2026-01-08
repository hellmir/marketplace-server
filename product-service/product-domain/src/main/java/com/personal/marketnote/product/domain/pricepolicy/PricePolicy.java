package com.personal.marketnote.product.domain.pricepolicy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class PricePolicy {
    private Long id;

    @JsonIgnore
    private Product product;

    private Long price;
    private Long discountPrice;
    private BigDecimal discountRate;
    private Long accumulatedPoint;
    private BigDecimal accumulationRate;
    private Long popularity;
    private EntityStatus status;
    private Long orderNum;
    private List<Long> optionIds;

    @JsonCreator
    private static PricePolicy jsonCreator(
            @JsonProperty("id") Long id,
            @JsonProperty("price") Long price,
            @JsonProperty("discountPrice") Long discountPrice,
            @JsonProperty("discountRate") BigDecimal discountRate,
            @JsonProperty("accumulatedPoint") Long accumulatedPoint,
            @JsonProperty("accumulationRate") BigDecimal accumulationRate,
            @JsonProperty("popularity") Long popularity,
            @JsonProperty("status") EntityStatus status,
            @JsonProperty("orderNum") Long orderNum,
            @JsonProperty("optionIds") List<Long> optionIds
    ) {
        return of(
                id, price, discountPrice, discountRate, accumulatedPoint,
                accumulationRate, popularity, status, orderNum, optionIds
        );
    }

    public static PricePolicy of(
            Product product,
            Long price,
            Long discountPrice,
            BigDecimal discountRate,
            Long accumulatedPoint,
            BigDecimal accumulationRate,
            Long popularity,
            EntityStatus status,
            Long orderNum
    ) {
        return PricePolicy.builder()
                .product(product)
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .accumulatedPoint(accumulatedPoint)
                .accumulationRate(accumulationRate)
                .popularity(popularity)
                .status(status)
                .orderNum(orderNum)
                .build();
    }

    public static PricePolicy of(
            Long id,
            Product product,
            Long price,
            Long discountPrice,
            BigDecimal discountRate,
            Long accumulatedPoint,
            BigDecimal accumulationRate,
            Long popularity,
            EntityStatus status,
            Long orderNum
    ) {
        return PricePolicy.builder()
                .id(id)
                .product(product)
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .accumulatedPoint(accumulatedPoint)
                .accumulationRate(accumulationRate)
                .popularity(popularity)
                .status(status)
                .orderNum(orderNum)
                .build();
    }

    public static PricePolicy of(
            Long id,
            Long price,
            Long discountPrice,
            BigDecimal discountRate,
            Long accumulatedPoint,
            BigDecimal accumulationRate,
            Long popularity,
            EntityStatus status,
            Long orderNum
    ) {
        return PricePolicy.builder()
                .id(id)
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .accumulatedPoint(accumulatedPoint)
                .accumulationRate(accumulationRate)
                .popularity(popularity)
                .status(status)
                .orderNum(orderNum)
                .build();
    }

    public static PricePolicy of(
            Long price,
            Long discountPrice,
            BigDecimal discountRate,
            Long accumulatedPoint,
            BigDecimal accumulationRate
    ) {
        return PricePolicy.builder()
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .accumulatedPoint(accumulatedPoint)
                .accumulationRate(accumulationRate)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static PricePolicy of(
            Product product,
            Long price,
            Long discountPrice,
            BigDecimal discountRate,
            Long accumulatedPoint,
            BigDecimal accumulationRate,
            List<Long> optionIds
    ) {
        return PricePolicy.builder()
                .product(product)
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .accumulatedPoint(accumulatedPoint)
                .accumulationRate(accumulationRate)
                .optionIds(optionIds)
                .build();
    }

    public static PricePolicy of(
            Long id,
            Long price,
            Long discountPrice,
            BigDecimal discountRate,
            Long accumulatedPoint,
            BigDecimal accumulationRate,
            Long popularity,
            EntityStatus status,
            Long orderNum,
            List<Long> optionIds
    ) {
        return PricePolicy.builder()
                .id(id)
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .accumulatedPoint(accumulatedPoint)
                .accumulationRate(accumulationRate)
                .popularity(popularity)
                .status(status)
                .orderNum(orderNum)
                .optionIds(optionIds)
                .build();
    }

    public boolean hasOptions() {
        return FormatValidator.hasValue(optionIds);
    }

    public void addProduct(Product product) {
        this.product = product;
    }

    public Long getProductId() {
        return product.getId();
    }
}
