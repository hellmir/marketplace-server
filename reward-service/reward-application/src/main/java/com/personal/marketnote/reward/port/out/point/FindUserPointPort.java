package com.personal.marketnote.reward.port.out.point;

public interface FindUserPointPort {
    boolean existsByUserId(Long userId);

    java.util.Optional<com.personal.marketnote.reward.domain.point.UserPoint> findByUserId(Long userId);
}
