package com.personal.marketnote.reward.adapter.out.persistence.offerwall;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.reward.adapter.out.mapper.RewardJpaEntityToDomainMapper;
import com.personal.marketnote.reward.adapter.out.persistence.offerwall.entity.OfferwallMapperJpaEntity;
import com.personal.marketnote.reward.adapter.out.persistence.offerwall.repository.OfferwallMapperJpaRepository;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.port.out.offerwall.SaveOfferwallMapperPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class OfferwallMapperPersistenceAdapter implements SaveOfferwallMapperPort {
    private final OfferwallMapperJpaRepository repository;

    @Override
    public OfferwallMapper save(OfferwallMapper offerwallMapper) {
        OfferwallMapperJpaEntity entity = OfferwallMapperJpaEntity.from(offerwallMapper);
        OfferwallMapperJpaEntity saved = repository.save(entity);
        return RewardJpaEntityToDomainMapper.mapToDomain(saved).orElse(null);
    }
}
