package com.personal.marketnote.commerce.adapter.out.persistence.servicecommunication;

import com.personal.marketnote.commerce.adapter.out.persistence.servicecommunication.entity.CommerceServiceCommunicationHistoryJpaEntity;
import com.personal.marketnote.commerce.adapter.out.persistence.servicecommunication.repository.CommerceServiceCommunicationHistoryJpaRepository;
import com.personal.marketnote.commerce.domain.servicecommunication.CommerceServiceCommunicationHistory;
import com.personal.marketnote.commerce.port.out.servicecommunication.SaveCommerceServiceCommunicationHistoryPort;
import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CommerceServiceCommunicationHistoryPersistenceAdapter
        implements SaveCommerceServiceCommunicationHistoryPort {
    private final CommerceServiceCommunicationHistoryJpaRepository repository;

    @Override
    public CommerceServiceCommunicationHistory save(CommerceServiceCommunicationHistory history) {
        CommerceServiceCommunicationHistoryJpaEntity savedEntity = repository.save(
                CommerceServiceCommunicationHistoryJpaEntity.from(history)
        );
        return savedEntity.toDomain();
    }
}
