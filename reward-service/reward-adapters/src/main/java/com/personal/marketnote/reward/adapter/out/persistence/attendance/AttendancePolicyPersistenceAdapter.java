package com.personal.marketnote.reward.adapter.out.persistence.attendance;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.reward.adapter.out.persistence.attendance.entity.AttendancePolicyJpaEntity;
import com.personal.marketnote.reward.adapter.out.persistence.attendance.repository.AttendancePolicyJpaRepository;
import com.personal.marketnote.reward.domain.attendance.AttendancePolicy;
import com.personal.marketnote.reward.port.out.attendance.FindAttendancePolicyPort;
import com.personal.marketnote.reward.port.out.attendance.SaveAttendancePolicyPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class AttendancePolicyPersistenceAdapter implements FindAttendancePolicyPort, SaveAttendancePolicyPort {
    private final AttendancePolicyJpaRepository repository;

    @Override
    public Optional<AttendancePolicy> findById(Short id) {
        return repository.findById(id).map(AttendancePolicyJpaEntity::toDomain);
    }

    @Override
    public Optional<AttendancePolicy> findByContinuousPeriodAndAttendenceDate(short continuousPeriod, LocalDate attendedDate) {
        return repository.findTop1ByContinuousPeriodAndAttendenceDate(continuousPeriod, attendedDate)
                .map(AttendancePolicyJpaEntity::toDomain);
    }

    @Override
    public Optional<AttendancePolicy> findByContinuousPeriodAndAttendenceDateIsNull(short continuousPeriod) {
        return repository.findTop1ByContinuousPeriodAndAttendenceDateIsNullOrderByOrderNumDesc(continuousPeriod)
                .map(AttendancePolicyJpaEntity::toDomain);
    }

    @Override
    @SuppressWarnings({"null", "DataFlowIssue"})
    public AttendancePolicy save(AttendancePolicy attendancePolicy) {
        AttendancePolicyJpaEntity savedEntity = java.util.Objects.requireNonNull(
                repository.save(AttendancePolicyJpaEntity.from(attendancePolicy)),
                "출석 정책 저장에 실패했습니다."
        );
        savedEntity.setIdToOrderNum();

        return savedEntity.toDomain();
    }
}

