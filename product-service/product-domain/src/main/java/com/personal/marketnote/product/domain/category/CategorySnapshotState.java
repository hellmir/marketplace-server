package com.personal.marketnote.product.domain.category;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategorySnapshotState {
    private final Long id;
    private final Long parentCategoryId;
    private final String name;
    private final EntityStatus status;
}

