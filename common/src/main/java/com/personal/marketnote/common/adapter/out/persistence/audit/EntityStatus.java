package com.personal.marketnote.common.adapter.out.persistence.audit;

public enum EntityStatus {
    INACTIVE("비활성화"),
    ACTIVE("활성화"),
    UNEXPOSED("비노출"),
    DELETED("삭제됨");

    private final String description;

    EntityStatus(String description) {
        this.description = description;
    }
}
