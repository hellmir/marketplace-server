package com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity;

import com.personal.marketnote.commerce.domain.inventory.Inventory;
import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "inventory")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InventoryJpaEntity extends BaseEntity {
    @Id
    @Column(name = "price_policy_id", nullable = false)
    private Long pricePolicyId;

    @Column(name = "stock", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer stock;

    // RDBMS 낙관적 락 기반의 동시성 제어
    @Version
    private Long version;

    private InventoryJpaEntity(Long pricePolicyId, Integer stock, Long version) {
        this.pricePolicyId = pricePolicyId;
        this.stock = stock;
        this.version = version;
    }

    public static InventoryJpaEntity from(Inventory inventory) {
        return new InventoryJpaEntity(
                inventory.getPricePolicyId(),
                inventory.getStockValue(),
                inventory.getVersion()
        );
    }

    public void updateFrom(Inventory inventory) {
        this.stock = inventory.getStockValue();
        this.version = inventory.getVersion();
    }
}
