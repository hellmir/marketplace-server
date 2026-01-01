package com.personal.marketnote.product.domain.product;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Product {
    private Long id;
    private Long sellerId;
    private String name;
    private String brandName;
    private String detail;
    private Long currentPrice;
    private Long accumulatedPoint;
    private Integer sales;
    private Long viewCount;
    private Long popularity;
    private Long orderNum;
    private EntityStatus status;

    public static Product of(
            Long sellerId, String name, String brandName, String detail, Long price, Long accumulatedPoint
    ) {
        return Product.builder()
                .sellerId(sellerId)
                .name(name)
                .brandName(brandName)
                .detail(detail)
                .currentPrice(price)
                .accumulatedPoint(accumulatedPoint)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static Product of(
            Long id,
            Long sellerId,
            String name,
            String brandName,
            String detail,
            Long currentPrice,
            Long accumulatedPoint,
            Integer sales,
            Long viewCount,
            Long popularity,
            Long orderNum,
            EntityStatus status
    ) {
        return Product.builder()
                .id(id)
                .sellerId(sellerId)
                .name(name)
                .brandName(brandName)
                .detail(detail)
                .currentPrice(currentPrice)
                .accumulatedPoint(accumulatedPoint)
                .sales(sales)
                .viewCount(viewCount)
                .popularity(popularity)
                .orderNum(orderNum)
                .status(status)
                .build();
    }

    public void update(String name, String brandName, String detail) {
        this.name = name;
        this.brandName = brandName;
        this.detail = detail;
    }
}


