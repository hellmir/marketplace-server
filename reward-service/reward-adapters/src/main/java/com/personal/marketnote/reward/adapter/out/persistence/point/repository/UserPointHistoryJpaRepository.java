package com.personal.marketnote.reward.adapter.out.persistence.point.repository;

import com.personal.marketnote.reward.adapter.out.persistence.point.entity.UserPointHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointHistoryJpaRepository extends JpaRepository<UserPointHistoryJpaEntity, Long> {
}
