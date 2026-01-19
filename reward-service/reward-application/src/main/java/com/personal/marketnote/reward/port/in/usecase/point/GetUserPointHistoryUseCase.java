package com.personal.marketnote.reward.port.in.usecase.point;

import com.personal.marketnote.reward.domain.point.UserPointHistoryFilter;
import com.personal.marketnote.reward.port.in.result.point.GetUserPointHistoryResult;

public interface GetUserPointHistoryUseCase {
    GetUserPointHistoryResult getUserPointHistories(Long userId, UserPointHistoryFilter filter);
}

