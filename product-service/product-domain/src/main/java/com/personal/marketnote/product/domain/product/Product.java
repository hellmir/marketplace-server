package com.personal.marketnote.product.domain.product;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Product {
    private Long id;
    private Long sellerId;
    private String name;
    private String brandName;
    private String detail;
    private Long price;
    private Long discountPrice;
    private BigDecimal discountRate;
    private Long accumulatedPoint;
    private Integer sales;
    private Long viewCount;
    private Long popularity;
    private boolean findAllOptionsYn;
    private Long orderNum;
    private EntityStatus status;

    public static Product of(
            Long sellerId, String name, String brandName, String detail, boolean isFindAllOptions
    ) {
        return Product.builder()
                .sellerId(sellerId)
                .name(name)
                .brandName(brandName)
                .detail(detail)
                .findAllOptionsYn(isFindAllOptions)
                .status(com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus.ACTIVE)
                .build();
    }

    public static Product of(
            Long id, Long sellerId, String name, String brandName, String detail, Long price, Long discountPrice,
            BigDecimal discountRate, Long accumulatedPoint, Integer sales, Long viewCount, Long popularity,
            boolean findAllOptionsYn, Long orderNum, EntityStatus status
    ) {
        return Product.builder()
                .id(id)
                .sellerId(sellerId)
                .name(name)
                .brandName(brandName)
                .detail(detail)
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .accumulatedPoint(accumulatedPoint)
                .sales(sales)
                .viewCount(viewCount)
                .popularity(popularity)
                .findAllOptionsYn(findAllOptionsYn)
                .orderNum(orderNum)
                .status(status)
                .build();
    }

    public void update(String name, String brandName, String detail, boolean isFindAllOptions) {
        this.name = name;
        this.brandName = brandName;
        this.detail = detail;
        findAllOptionsYn = isFindAllOptions;
    }
}


