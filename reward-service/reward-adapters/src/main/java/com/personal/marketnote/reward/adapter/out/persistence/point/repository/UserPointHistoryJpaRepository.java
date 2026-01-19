package com.personal.marketnote.reward.adapter.out.persistence.point.repository;

import com.personal.marketnote.reward.adapter.out.persistence.point.entity.UserPointHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPointHistoryJpaRepository extends JpaRepository<UserPointHistoryJpaEntity, Long> {
    List<UserPointHistoryJpaEntity> findByUserIdOrderByAccumulatedAtDesc(Long userId);

    List<UserPointHistoryJpaEntity> findByUserIdAndAmountGreaterThanOrderByAccumulatedAtDesc(Long userId, Long amount);

    List<UserPointHistoryJpaEntity> findByUserIdAndAmountLessThanOrderByAccumulatedAtDesc(Long userId, Long amount);
}
