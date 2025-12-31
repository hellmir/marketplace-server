package com.personal.marketnote.product.adapter.out.persistence.category.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseGeneralEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "category", indexes = {
        @Index(name = "idx_category_parent", columnList = "parent_category_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CategoryJpaEntity extends BaseGeneralEntity {
    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    @Column(name = "name", nullable = false, length = 127)
    private String name;
}


