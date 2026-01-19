package com.personal.marketnote.reward.port.out.point;

import com.personal.marketnote.reward.domain.point.UserPointHistory;
import com.personal.marketnote.reward.domain.point.UserPointHistoryFilter;

import java.util.List;

public interface FindUserPointHistoryPort {
    List<UserPointHistory> findByUserId(Long userId, UserPointHistoryFilter filter);
}

