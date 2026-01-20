package com.personal.marketnote.reward.adapter.out.persistence.attendance.repository;

import com.personal.marketnote.reward.adapter.out.persistence.attendance.entity.AttendancePolicyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AttendancePolicyJpaRepository extends JpaRepository<AttendancePolicyJpaEntity, Short> {
    Optional<AttendancePolicyJpaEntity> findTop1ByContinuousPeriodAndAttendenceDate(short continuousPeriod, LocalDateTime attendenceDate);

    Optional<AttendancePolicyJpaEntity> findTop1ByContinuousPeriodAndAttendenceDateIsNull(short continuousPeriod);
}

