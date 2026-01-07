package com.personal.marketnote.commerce.service.inventory;

import com.personal.marketnote.commerce.domain.inventory.Inventory;
import com.personal.marketnote.commerce.port.in.usecase.inventory.GetInventoryUseCase;
import com.personal.marketnote.commerce.port.out.inventory.FindInventoryPort;
import com.personal.marketnote.common.application.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetInventoryService implements GetInventoryUseCase {
    private final FindInventoryPort findInventoryPort;

    @Override
    public Set<Inventory> getInventories(List<Long> pricePolicyIds) {
        return findInventoryPort.findByPricePolicyIds(new HashSet<>(pricePolicyIds));
    }
}
