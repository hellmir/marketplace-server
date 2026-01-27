package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoSupplierCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoSupplierCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoSupplierResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.UpdateFasstoSupplierUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.UpdateFasstoSupplierPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class UpdateFasstoSupplierService implements UpdateFasstoSupplierUseCase {
    private final UpdateFasstoSupplierPort updateFasstoSupplierPort;

    @Override
    public UpdateFasstoSupplierResult updateSupplier(UpdateFasstoSupplierCommand command) {
        return updateFasstoSupplierPort.updateSupplier(
                FasstoSupplierCommandToRequestMapper.mapToUpdateRequest(command)
        );
    }
}
