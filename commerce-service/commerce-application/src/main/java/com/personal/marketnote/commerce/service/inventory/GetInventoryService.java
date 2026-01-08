package com.personal.marketnote.commerce.service.inventory;

import com.personal.marketnote.commerce.domain.inventory.Inventory;
import com.personal.marketnote.commerce.port.in.command.inventory.RegisterInventoryCommand;
import com.personal.marketnote.commerce.port.in.usecase.inventory.GetInventoryUseCase;
import com.personal.marketnote.commerce.port.in.usecase.inventory.RegisterInventoryUseCase;
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
    private final RegisterInventoryUseCase registerInventoryUseCase;
    private final FindInventoryPort findInventoryPort;

    @Override
    public Set<Inventory> getInventories(List<Long> pricePolicyIds) {
        Set<Inventory> inventories = findInventoryPort.findByPricePolicyIds(new HashSet<>(pricePolicyIds));

        // FIXME: Kafka 이벤트 Production으로 변경
        // 존재하지 않는 재고 튜플/Cache Memory 신규 생성
        if (inventories.size() != pricePolicyIds.size()) {
            Set<RegisterInventoryCommand> commands = new HashSet<>();
            pricePolicyIds.stream()
                    .filter(pricePolicyId -> inventories.stream()
                            .filter(inventory -> inventory.isMe(pricePolicyId))
                            .findFirst()
                            .isEmpty())
                    .forEach(pricePolicyId -> commands.add(RegisterInventoryCommand.of(pricePolicyId)));

            inventories.addAll(registerInventoryUseCase.registerInventories(commands));
        }

        return inventories;
    }
}
