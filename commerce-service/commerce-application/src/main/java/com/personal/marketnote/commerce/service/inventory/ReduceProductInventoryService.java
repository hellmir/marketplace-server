package com.personal.marketnote.commerce.service.inventory;

import com.personal.marketnote.commerce.domain.inventory.Inventory;
import com.personal.marketnote.commerce.domain.inventory.InventoryDeductionHistories;
import com.personal.marketnote.commerce.domain.order.OrderProduct;
import com.personal.marketnote.commerce.port.in.usecase.inventory.ReduceProductInventoryUseCase;
import com.personal.marketnote.commerce.port.out.inventory.FindInventoryPort;
import com.personal.marketnote.commerce.port.out.inventory.SaveInventoryDeductionHistoryPort;
import com.personal.marketnote.commerce.port.out.inventory.UpdateInventoryPort;
import com.personal.marketnote.common.application.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class ReduceProductInventoryService implements ReduceProductInventoryUseCase {
    private final FindInventoryPort findInventoryPort;
    private final UpdateInventoryPort updateInventoryPort;
    private final SaveInventoryDeductionHistoryPort saveInventoryDeductionHistoryPort;

    @Override
    public void reduce(List<OrderProduct> orderProducts, String reason) {
        Map<Long, Integer> pricePolicyQuantities = orderProducts.stream()
                .collect(
                        Collectors.groupingBy(
                                OrderProduct::getPricePolicyId, Collectors.summingInt(OrderProduct::getQuantity)
                        )
                );
        Set<Inventory> inventories = findInventoryPort.findByPricePolicyIds(pricePolicyQuantities.keySet());
        inventories.forEach(inventory -> inventory.reduce(pricePolicyQuantities.get(inventory.getPricePolicyId())));

        updateInventoryPort.update(inventories);
        saveInventoryDeductionHistoryPort.save(
                InventoryDeductionHistories.from(pricePolicyQuantities, reason)
        );
    }
}
