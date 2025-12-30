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
    private String detail;
    private Integer sales;
    private Long orderNum;
    private EntityStatus status;

    public static Product of(Long sellerId, String name, String detail) {
        return Product.builder()
                .sellerId(sellerId)
                .name(name)
                .detail(detail)
                .sales(0)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static Product of(
            Long id,
            Long sellerId,
            String name,
            String detail,
            Integer sales,
            Long orderNum,
            EntityStatus status
    ) {
        return Product.builder()
                .id(id)
                .sellerId(sellerId)
                .name(name)
                .detail(detail)
                .sales(sales)
                .orderNum(orderNum)
                .status(status)
                .build();
    }
}


