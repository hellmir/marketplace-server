package com.personal.marketnote.commerce.service.inventory;

import com.personal.marketnote.commerce.port.in.command.inventory.RegisterInventoryCommand;
import com.personal.marketnote.commerce.port.in.usecase.inventory.RegisterInventoryUseCase;
import com.personal.marketnote.commerce.port.out.inventory.SaveInventoryPort;
import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.product.domain.inventory.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class RegisterInventoryService implements RegisterInventoryUseCase {
    private final SaveInventoryPort saveInventoryPort;

    @Override
    public void registerInventory(RegisterInventoryCommand command) {
        Inventory inventory = Inventory.of(command.pricePolicyId());
        saveInventoryPort.save(inventory);
    }
}
