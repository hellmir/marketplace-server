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
    private Long parentCategoryId;
    private String name;
    private EntityStatus status;

    public static Category from(CategoryCreateState state) {
        EntityStatus status = state.getStatus() != null ? state.getStatus() : EntityStatus.ACTIVE;
        return Category.builder()
                .parentCategoryId(state.getParentCategoryId())
                .name(state.getName())
                .status(status)
                .build();
    }

    public static Category from(CategorySnapshotState state) {
        return Category.builder()
                .id(state.getId())
                .parentCategoryId(state.getParentCategoryId())
                .name(state.getName())
                .status(state.getStatus())
                .build();
    }

    public void delete() {
        status = EntityStatus.INACTIVE;
    }

    public boolean isInactive() {
        return status.isInactive();
    }
}
