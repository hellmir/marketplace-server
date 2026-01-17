package com.personal.marketnote.reward.port.out.point;

import com.personal.marketnote.reward.domain.point.UserPoint;

public interface SaveUserPointPort {
    UserPoint save(UserPoint userPoint);
}
