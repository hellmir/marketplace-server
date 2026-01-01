package com.personal.marketnote.product.domain.product;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductOption {
    private Long id;
    private ProductOptionCategory category;
    private String content;
    private Long price;
    private Long accumulatedPoint;
    private EntityStatus status;

    public static ProductOption of(ProductOptionCategory category, String content, Long price, Long accumulatedPoint) {
        return ProductOption.builder()
                .category(category)
                .content(content)
                .price(price)
                .accumulatedPoint(accumulatedPoint)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static ProductOption of(
            Long id,
            ProductOptionCategory category,
            String content,
            Long price,
            Long accumulatedPoint,
            EntityStatus status
    ) {
        return ProductOption.builder()
                .id(id)
                .category(category)
                .content(content)
                .price(price)
                .accumulatedPoint(accumulatedPoint)
                .status(status)
                .build();
    }

    public static ProductOption of(String content, Long price, Long accumulatedPoint) {
        return ProductOption.builder()
                .content(content)
                .price(price)
                .accumulatedPoint(accumulatedPoint)
                .status(EntityStatus.ACTIVE)
                .build();
    }
}
