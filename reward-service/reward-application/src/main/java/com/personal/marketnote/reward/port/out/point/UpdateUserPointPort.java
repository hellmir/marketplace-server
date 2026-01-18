package com.personal.marketnote.reward.port.out.point;

import com.personal.marketnote.reward.domain.point.UserPoint;
import com.personal.marketnote.reward.exception.UserPointNotFoundException;

public interface UpdateUserPointPort {
    UserPoint update(UserPoint userPoint) throws UserPointNotFoundException;
}
