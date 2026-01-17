package com.personal.marketnote.reward.adapter.out.persistence.point;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.reward.adapter.out.persistence.point.entity.UserPointJpaEntity;
import com.personal.marketnote.reward.adapter.out.persistence.point.repository.UserPointJpaRepository;
import com.personal.marketnote.reward.domain.point.UserPoint;
import com.personal.marketnote.reward.port.out.point.FindUserPointPort;
import com.personal.marketnote.reward.port.out.point.SaveUserPointPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPointPersistenceAdapter implements SaveUserPointPort, FindUserPointPort {
    private final UserPointJpaRepository repository;

    @Override
    public UserPoint save(UserPoint userPoint) {
        UserPointJpaEntity saved = repository.save(UserPointJpaEntity.from(userPoint));
        return saved.toDomain();
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return repository.existsByUserId(userId);
    }
}
