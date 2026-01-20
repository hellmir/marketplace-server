package com.personal.marketnote.reward.adapter.out.persistence.attendance;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.reward.adapter.out.persistence.attendance.entity.UserAttendanceHistoryJpaEntity;
import com.personal.marketnote.reward.adapter.out.persistence.attendance.repository.UserAttendanceHistoryJpaRepository;
import com.personal.marketnote.reward.domain.attendance.UserAttendanceHistory;
import com.personal.marketnote.reward.port.out.attendance.SaveUserAttendanceHistoryPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserAttendanceHistoryPersistenceAdapter implements SaveUserAttendanceHistoryPort {
    private final UserAttendanceHistoryJpaRepository repository;

    @Override
    @SuppressWarnings({"null", "DataFlowIssue"})
    public UserAttendanceHistory save(UserAttendanceHistory history) {
        UserAttendanceHistoryJpaEntity saved = java.util.Objects.requireNonNull(
                repository.save(UserAttendanceHistoryJpaEntity.from(history)),
                "출석 기록 저장에 실패했습니다."
        );
        return saved.toDomain();
    }
}

