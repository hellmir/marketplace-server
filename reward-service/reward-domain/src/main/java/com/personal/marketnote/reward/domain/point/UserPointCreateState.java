package com.personal.marketnote.reward.domain.point;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPointCreateState {
    private final Long userId;
    private final String userKey;
    private final Long amount;
    private final Long addExpectedAmount;
    private final Long expireExpectedAmount;
}
