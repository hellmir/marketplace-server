package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoWarehouseCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoWarehouseCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehouseResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.UpdateFasstoWarehouseUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.UpdateFasstoWarehousePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class UpdateFasstoWarehouseService implements UpdateFasstoWarehouseUseCase {
    private final UpdateFasstoWarehousePort updateFasstoWarehousePort;

    @Override
    public UpdateFasstoWarehouseResult updateWarehouse(UpdateFasstoWarehouseCommand command) {
        return updateFasstoWarehousePort.updateWarehouse(
                FasstoWarehouseCommandToRequestMapper.mapToUpdateRequest(command)
        );
    }
}
