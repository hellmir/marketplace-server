package com.personal.shopreward.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.GenerationType.IDENTITY;

@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseGeneralEntity extends BaseEntity {
    protected BaseGeneralEntity() {
        status = EntityStatus.ACTIVE;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private EntityStatus status;

    protected void hideEntity() {
        status = EntityStatus.UNEXPOSED;
    }

    protected void deleteEntity() {
        status = EntityStatus.DELETED;
    }

    protected void activateEntity() {
        status = EntityStatus.ACTIVE;
    }

    protected void deactivateEntity() {
        status = EntityStatus.INACTIVE;
    }
}
