package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoWarehousingCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoWarehousingUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.RegisterFasstoWarehousingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class RegisterFasstoWarehousingService implements RegisterFasstoWarehousingUseCase {
    private final RegisterFasstoWarehousingPort registerFasstoWarehousingPort;

    @Override
    public RegisterFasstoWarehousingResult registerWarehousing(RegisterFasstoWarehousingCommand command) {
        return registerFasstoWarehousingPort.registerWarehousing(
                FasstoWarehousingCommandToRequestMapper.mapToRegisterRequest(command)
        );
    }
}
