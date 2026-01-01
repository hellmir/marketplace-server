package com.personal.marketnote.product.domain.product;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProductOptionCategory {
    private Long id;
    private Product product;
    private String name;
    private List<ProductOption> options;
    private Long orderNum;
    private EntityStatus status;

    public static ProductOptionCategory of(Product product, String name, List<ProductOption> options) {
        return ProductOptionCategory.builder()
                .product(product)
                .name(name)
                .options(options)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static ProductOptionCategory of(
            Long id,
            Product product,
            String name,
            List<ProductOption> options,
            Long orderNum,
            EntityStatus status
    ) {
        return ProductOptionCategory.builder()
                .id(id)
                .product(product)
                .name(name)
                .options(options)
                .orderNum(orderNum)
                .status(status)
                .build();
    }
}
