package com.personal.marketnote.reward.port.out.point;

import com.personal.marketnote.reward.domain.point.UserPoint;

import java.util.Optional;

public interface FindUserPointPort {
    boolean existsByUserId(Long userId);

    boolean existsByUserKey(String userKey);

    Optional<UserPoint> findByUserId(Long userId);

    Optional<UserPoint> findByUserKey(String userKey);
}
