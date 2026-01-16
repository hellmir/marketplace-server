package com.personal.marketnote.reward.adapter.out.persistence.offerwall;

import com.fasterxml.jackson.databind.JsonNode;
import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.adapter.out.persistence.offerwall.entity.OfferwallMapperJpaEntity;
import com.personal.marketnote.reward.adapter.out.persistence.offerwall.repository.OfferwallMapperJpaRepository;
import com.personal.marketnote.reward.domain.offerwall.OfferwallMapper;
import com.personal.marketnote.reward.port.out.offerwall.SaveOfferwallMapperPort;
import com.personal.marketnote.reward.port.out.offerwall.UpdateOfferwallMapperResponsePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class OfferwallMapperPersistenceAdapter implements SaveOfferwallMapperPort, UpdateOfferwallMapperResponsePort {
    private final OfferwallMapperJpaRepository repository;

    @Override
    public OfferwallMapper save(OfferwallMapper offerwallMapper) {
        OfferwallMapperJpaEntity saved = repository.save(OfferwallMapperJpaEntity.from(offerwallMapper));
        return saved.toDomain();
    }

    @Override
    public OfferwallMapper updateResponse(Long id, String responsePayload, JsonNode responsePayloadJson) {
        Optional<OfferwallMapperJpaEntity> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) {
            return null;
        }

        OfferwallMapperJpaEntity entity = entityOpt.get();
        if (FormatValidator.hasValue(responsePayload)) {
            entity.updateResponse(responsePayload, responsePayloadJson);
        }

        return repository.save(entity).toDomain();
    }
}
