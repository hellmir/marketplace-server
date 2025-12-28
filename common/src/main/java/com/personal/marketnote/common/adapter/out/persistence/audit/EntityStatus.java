package com.personal.marketnote.common.adapter.out.persistence.audit;

public enum EntityStatus {
    ACTIVE("활성화"),
    INACTIVE("비활성화"),
    UNEXPOSED("비노출");

    private final String description;

    EntityStatus(String description) {
        this.description = description;
    }
}
