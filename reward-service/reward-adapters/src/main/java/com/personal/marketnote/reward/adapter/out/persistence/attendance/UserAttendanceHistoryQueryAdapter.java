package com.personal.marketnote.reward.adapter.out.persistence.attendance;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.reward.adapter.out.persistence.attendance.repository.UserAttendanceHistoryJpaRepository;
import com.personal.marketnote.reward.domain.attendance.UserAttendanceHistory;
import com.personal.marketnote.reward.port.out.attendance.FindUserAttendanceHistoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserAttendanceHistoryQueryAdapter implements FindUserAttendanceHistoryPort {
    private final UserAttendanceHistoryJpaRepository repository;

    @Override
    public Optional<UserAttendanceHistory> findLatestByUserId(Long userId) {
        return repository.findTop1ByUserIdOrderByAttendedAtDesc(userId)
                .map(entity -> entity.toDomain());
    }
}

