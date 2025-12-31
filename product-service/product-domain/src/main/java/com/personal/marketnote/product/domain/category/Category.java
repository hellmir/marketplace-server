package com.personal.marketnote.product.domain.category;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Category {
    private Long id;
    private Long parentCategoryId; // null이면 루트
    private String name;
    private EntityStatus status;

    public static Category of(Long id, Long parentCategoryId, String name, EntityStatus status) {
        return Category.builder()
                .id(id)
                .parentCategoryId(parentCategoryId)
                .name(name)
                .status(status)
                .build();
    }
}
