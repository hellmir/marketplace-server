package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoWarehouseCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousesCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousesResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoWarehousesUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoWarehousesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoWarehousesService implements GetFasstoWarehousesUseCase {
    private final GetFasstoWarehousesPort getFasstoWarehousesPort;

    @Override
    public GetFasstoWarehousesResult getWarehouses(GetFasstoWarehousesCommand command) {
        return getFasstoWarehousesPort.getWarehouses(
                FasstoWarehouseCommandToRequestMapper.mapToWarehousesQuery(command)
        );
    }
}
