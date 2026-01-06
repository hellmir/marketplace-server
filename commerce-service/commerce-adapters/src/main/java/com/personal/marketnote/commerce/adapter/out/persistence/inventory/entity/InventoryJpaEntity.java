package com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity;

import com.personal.marketnote.commerce.domain.inventory.Inventory;
import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "inventory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
public class InventoryJpaEntity extends BaseEntity {
    @Id
    @Column(name = "price_policy_id", nullable = false)
    private Long pricePolicyId;

    @Column(name = "quantity", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer quantity;

    private InventoryJpaEntity(Long pricePolicyId) {
        this.pricePolicyId = pricePolicyId;
    }

    public static InventoryJpaEntity from(Inventory inventory) {
        return new InventoryJpaEntity(inventory.getPricePolicyId());
    }

    public void updateFrom(Inventory inventory) {
        quantity = inventory.getQuantity().getValue();
    }
}

