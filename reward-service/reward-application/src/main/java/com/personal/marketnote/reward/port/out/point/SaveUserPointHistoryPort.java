package com.personal.marketnote.reward.port.out.point;

import com.personal.marketnote.reward.domain.point.UserPointHistory;

public interface SaveUserPointHistoryPort {
    UserPointHistory save(UserPointHistory history);
}
