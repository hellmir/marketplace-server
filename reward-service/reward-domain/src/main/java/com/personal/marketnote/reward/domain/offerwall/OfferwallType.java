package com.personal.marketnote.reward.domain.offerwall;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OfferwallType {
    ADPOPCORN("애드팝콘"),
    TNK("TNK");

    private final String description;

    public boolean isAdpopcorn() {
        return this == ADPOPCORN;
    }

    public boolean isTnk() {
        return this == TNK;
    }
}
