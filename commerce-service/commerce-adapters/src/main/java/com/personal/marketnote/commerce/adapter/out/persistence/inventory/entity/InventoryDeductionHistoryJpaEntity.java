package com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity;

import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "inventory_deduction_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@DynamicInsert
@DynamicUpdate
public class InventoryDeductionHistoryJpaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "price_policy_id", nullable = false)
    private Long pricePolicyId;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "reason", length = 511)
    private String reason;
}

