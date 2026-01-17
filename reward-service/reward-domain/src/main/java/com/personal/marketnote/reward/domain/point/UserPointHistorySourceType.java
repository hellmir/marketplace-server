package com.personal.marketnote.reward.domain.point;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserPointHistorySourceType {
    USER("USER"),
    ATTENDENCE("ATTENDENCE"),
    OFFERWALL("OFFERWALL"),
    GAME("GAME"),
    PRODUCT("PRODUCT"),
    ORDER("ORDER");

    private final String description;
}
