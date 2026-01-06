package com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "inventory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@DynamicInsert
@DynamicUpdate
public class InventoryJpaEntity extends BaseEntity {
    @Id
    @Column(name = "price_policy_id", nullable = false)
    private Long pricePolicyId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}

