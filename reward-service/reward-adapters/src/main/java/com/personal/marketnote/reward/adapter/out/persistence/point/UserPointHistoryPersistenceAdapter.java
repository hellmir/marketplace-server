package com.personal.marketnote.reward.adapter.out.persistence.point;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.reward.adapter.out.persistence.point.entity.UserPointHistoryJpaEntity;
import com.personal.marketnote.reward.adapter.out.persistence.point.repository.UserPointHistoryJpaRepository;
import com.personal.marketnote.reward.domain.point.UserPointHistory;
import com.personal.marketnote.reward.port.out.point.SaveUserPointHistoryPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPointHistoryPersistenceAdapter implements SaveUserPointHistoryPort {
    private final UserPointHistoryJpaRepository repository;

    @Override
    public UserPointHistory save(UserPointHistory history) {
        UserPointHistoryJpaEntity saved = repository.save(UserPointHistoryJpaEntity.from(history));
        return saved.toDomain();
    }
}
