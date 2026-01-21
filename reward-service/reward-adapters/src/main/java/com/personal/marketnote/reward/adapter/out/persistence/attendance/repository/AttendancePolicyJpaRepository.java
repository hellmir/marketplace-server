package com.personal.marketnote.reward.adapter.out.persistence.attendance.repository;

import com.personal.marketnote.reward.adapter.out.persistence.attendance.entity.AttendancePolicyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendancePolicyJpaRepository extends JpaRepository<AttendancePolicyJpaEntity, Short> {
    Optional<AttendancePolicyJpaEntity> findTop1ByContinuousPeriodAndAttendenceDate(short continuousPeriod, LocalDate attendenceDate);

    Optional<AttendancePolicyJpaEntity> findTop1ByContinuousPeriodAndAttendenceDateIsNullOrderByOrderNumDesc(short continuousPeriod);
}

