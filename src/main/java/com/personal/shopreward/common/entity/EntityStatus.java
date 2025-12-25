package com.personal.shopreward.common.entity;

public enum EntityStatus {
    ACTIVE("활성화"),
    INACTIVE("비활성화"),
    UNEXPOSED("비노출"),
    DELETED("삭제됨");

    private final String description;

    EntityStatus(String description) {
        this.description = description;
    }
}
