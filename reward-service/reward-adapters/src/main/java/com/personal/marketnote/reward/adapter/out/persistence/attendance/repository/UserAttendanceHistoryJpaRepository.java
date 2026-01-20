package com.personal.marketnote.reward.adapter.out.persistence.attendance.repository;

import com.personal.marketnote.reward.adapter.out.persistence.attendance.entity.UserAttendanceHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAttendanceHistoryJpaRepository extends JpaRepository<UserAttendanceHistoryJpaEntity, Long> {
    Optional<UserAttendanceHistoryJpaEntity> findTop1ByUserIdOrderByAttendedAtDesc(Long userId);
}

