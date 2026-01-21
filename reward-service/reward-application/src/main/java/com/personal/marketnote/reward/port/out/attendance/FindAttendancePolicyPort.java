package com.personal.marketnote.reward.port.out.attendance;

import com.personal.marketnote.reward.domain.attendance.AttendancePolicy;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FindAttendancePolicyPort {
    Optional<AttendancePolicy> findById(Short id);

    Optional<AttendancePolicy> findByContinuousPeriodAndAttendenceDate(short continuousPeriod, LocalDate attendedDate);

    Optional<AttendancePolicy> findByContinuousPeriodAndAttendenceDateIsNull(short continuousPeriod);

    List<AttendancePolicy> findAllOrderByOrderNumDesc();
}

