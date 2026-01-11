package com.personal.marketnote.product.domain.pricepolicy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.option.ProductOption;
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
    private List<ProductOption> productOptions;

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
        return from(
                PricePolicySnapshotState.builder()
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
                        .build()
        );
    }

    public static PricePolicy from(PricePolicyCreateState state) {
        EntityStatus status = state.getStatus() != null ? state.getStatus() : EntityStatus.ACTIVE;
        return PricePolicy.builder()
                .product(state.getProduct())
                .price(state.getPrice())
                .discountPrice(state.getDiscountPrice())
                .discountRate(state.getDiscountRate())
                .accumulatedPoint(state.getAccumulatedPoint())
                .accumulationRate(state.getAccumulationRate())
                .popularity(state.getPopularity())
                .status(status)
                .orderNum(state.getOrderNum())
                .optionIds(state.getOptionIds())
                .build();
    }

    public static PricePolicy from(PricePolicySnapshotState state) {
        return PricePolicy.builder()
                .id(state.getId())
                .product(state.getProduct())
                .price(state.getPrice())
                .discountPrice(state.getDiscountPrice())
                .discountRate(state.getDiscountRate())
                .accumulatedPoint(state.getAccumulatedPoint())
                .accumulationRate(state.getAccumulationRate())
                .popularity(state.getPopularity())
                .status(state.getStatus())
                .orderNum(state.getOrderNum())
                .optionIds(state.getOptionIds())
                .productOptions(state.getProductOptions())
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
