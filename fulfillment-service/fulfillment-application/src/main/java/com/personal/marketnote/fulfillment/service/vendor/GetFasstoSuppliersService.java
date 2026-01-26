package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoSupplierCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoSuppliersCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoSuppliersResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoSuppliersUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoSuppliersPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoSuppliersService implements GetFasstoSuppliersUseCase {
    private final GetFasstoSuppliersPort getFasstoSuppliersPort;

    @Override
    public GetFasstoSuppliersResult getSuppliers(GetFasstoSuppliersCommand command) {
        return getFasstoSuppliersPort.getSuppliers(
                FasstoSupplierCommandToRequestMapper.mapToSuppliersQuery(command)
        );
    }
}
