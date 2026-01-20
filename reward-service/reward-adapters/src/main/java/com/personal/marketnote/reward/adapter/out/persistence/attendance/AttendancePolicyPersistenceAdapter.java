package com.personal.marketnote.reward.adapter.out.persistence.attendance;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.reward.adapter.out.persistence.attendance.entity.AttendancePolicyJpaEntity;
import com.personal.marketnote.reward.adapter.out.persistence.attendance.repository.AttendancePolicyJpaRepository;
import com.personal.marketnote.reward.domain.attendance.AttendancePolicy;
import com.personal.marketnote.reward.port.out.attendance.FindAttendancePolicyPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class AttendancePolicyPersistenceAdapter implements FindAttendancePolicyPort {
    private final AttendancePolicyJpaRepository repository;

    @Override
    public Optional<AttendancePolicy> findById(Short id) {
        if (id == null) {
            return Optional.empty();
        }
        return repository.findById(id).map(AttendancePolicyJpaEntity::toDomain);
    }

    @Override
    public Optional<AttendancePolicy> findByContinuousPeriodAndAttendenceDate(short continuousPeriod, LocalDate attendedDate) {
        LocalDateTime startOfDay = attendedDate.atStartOfDay();
        return repository.findTop1ByContinuousPeriodAndAttendenceDate(continuousPeriod, startOfDay)
                .map(AttendancePolicyJpaEntity::toDomain);
    }

    @Override
    public Optional<AttendancePolicy> findByContinuousPeriodAndAttendenceDateIsNull(short continuousPeriod) {
        return repository.findTop1ByContinuousPeriodAndAttendenceDateIsNull(continuousPeriod)
                .map(AttendancePolicyJpaEntity::toDomain);
    }
}

