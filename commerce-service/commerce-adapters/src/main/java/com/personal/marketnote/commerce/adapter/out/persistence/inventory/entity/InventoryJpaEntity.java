package com.personal.marketnote.commerce.adapter.out.persistence.inventory.entity;

import com.personal.marketnote.commerce.domain.inventory.Inventory;
import com.personal.marketnote.common.adapter.out.persistence.audit.BaseEntity;
import com.personal.marketnote.common.utility.FormatValidator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "inventory")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class InventoryJpaEntity extends BaseEntity {
    @Column(name = "product_id")
    private Long productId;

    @Id
    @Column(name = "price_policy_id", nullable = false)
    private Long pricePolicyId;

    @Column(name = "stock", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer stock;

    // RDBMS 낙관적 락 기반의 동시성 제어
    @Version
    @Column(name = "version", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long version;

    public static InventoryJpaEntity from(Inventory inventory) {
        Long version = inventory.getVersion();
        if (FormatValidator.hasNoValue(version)) {
            version = 0L;
        }

        return new InventoryJpaEntity(
                inventory.getProductId(),
                inventory.getPricePolicyId(),
                inventory.getStockValue(),
                version
        );
    }

    public void updateFrom(Inventory inventory) {
        if (FormatValidator.hasNoValue(version)) {
            // 초기 버전 미설정 데이터 보호
            version = 0L;
        }

        stock = inventory.getStockValue();
    }

    @PostLoad
    private void initVersionAfterLoad() {
        if (FormatValidator.hasNoValue(version)) {
            version = 0L;
        }
    }

    @PrePersist
    private void initVersionBeforePersist() {
        if (FormatValidator.hasNoValue(version)) {
            version = 0L;
        }
    }
}
