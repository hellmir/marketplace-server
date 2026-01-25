package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoSupplierCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoSupplierCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoSupplierResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoSupplierUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.RegisterFasstoSupplierPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class RegisterFasstoSupplierService implements RegisterFasstoSupplierUseCase {
    private final RegisterFasstoSupplierPort registerFasstoSupplierPort;

    @Override
    public RegisterFasstoSupplierResult registerSupplier(RegisterFasstoSupplierCommand command) {
        return registerFasstoSupplierPort.registerSupplier(
                FasstoSupplierCommandToRequestMapper.mapToRegisterRequest(command)
        );
    }
}
