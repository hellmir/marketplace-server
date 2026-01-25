package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoWarehouseCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehouseCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehouseResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoWarehouseUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.RegisterFasstoWarehousePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class RegisterFasstoWarehouseService implements RegisterFasstoWarehouseUseCase {
    private final RegisterFasstoWarehousePort registerFasstoWarehousePort;

    @Override
    public RegisterFasstoWarehouseResult registerWarehouse(RegisterFasstoWarehouseCommand command) {
        return registerFasstoWarehousePort.registerWarehouse(
                FasstoWarehouseCommandToRequestMapper.mapToRegisterRequest(command)
        );
    }
}
