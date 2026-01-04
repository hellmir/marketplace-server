package com.personal.marketnote.product.domain.option;

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
    private EntityStatus status;

    public static ProductOption of(ProductOptionCategory category, String content) {
        return ProductOption.builder()
                .category(category)
                .content(content)
                .status(EntityStatus.ACTIVE)
                .build();
    }

    public static ProductOption of(
            Long id,
            ProductOptionCategory category,
            String content,
            EntityStatus status
    ) {
        return ProductOption.builder()
                .id(id)
                .category(category)
                .content(content)
                .status(status)
                .build();
    }

    public static ProductOption of(String content) {
        return ProductOption.builder()
                .content(content)
                .status(EntityStatus.ACTIVE)
                .build();
    }
}
