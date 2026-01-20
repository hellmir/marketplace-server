package com.personal.marketnote.reward.domain.attendance;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public enum AttendancePolicyStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    UNEXPOSED("UNEXPOSED");

    private final String description;
}

