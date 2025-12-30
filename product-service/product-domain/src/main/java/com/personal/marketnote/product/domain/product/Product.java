package com.personal.marketnote.product.domain.product;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.utility.FormatValidator;
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
    private Long orderNumber;
    private EntityStatus status;

    public static Product create(Long sellerId, String name, String detail, Long orderNumber) {
        if (!FormatValidator.hasValue(sellerId) || sellerId <= 0) {
            throw new IllegalArgumentException("sellerId must be positive");
        }
        if (!FormatValidator.hasValue(name)) {
            throw new IllegalArgumentException("name must not be empty");
        }

        return Product.builder()
                .sellerId(sellerId)
                .name(name)
                .detail(detail)
                .sales(0)
                .orderNumber(orderNumber)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static Product of(
            Long id,
            Long sellerId,
            String name,
            String detail,
            Integer sales,
            Long orderNumber,
            EntityStatus status
    ) {
        return Product.builder()
                .id(id)
                .sellerId(sellerId)
                .name(name)
                .detail(detail)
                .sales(sales)
                .orderNumber(orderNumber)
                .status(status)
                .build();
    }
}


