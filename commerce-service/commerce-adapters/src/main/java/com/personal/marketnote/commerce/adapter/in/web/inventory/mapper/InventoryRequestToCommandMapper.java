package com.personal.marketnote.commerce.adapter.in.web.inventory.mapper;

import com.personal.marketnote.commerce.port.in.command.inventory.RegisterInventoryCommand;
import com.personal.marketnote.common.adapter.in.request.RegisterInventoryRequest;

public class InventoryRequestToCommandMapper {
    public static RegisterInventoryCommand mapToCommand(
            RegisterInventoryRequest request
    ) {
        return RegisterInventoryCommand.of(request.getPricePolicyId());
    }
}

