package com.personal.marketnote.reward.domain.attendance;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public enum AttendanceRewardType {
    POINT("POINT"),
    BOOSTER("BOOSTER");

    private final String description;
}

