package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoShopCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoShopCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoShopResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoShopUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.RegisterFasstoShopPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class RegisterFasstoShopService implements RegisterFasstoShopUseCase {
    private final RegisterFasstoShopPort registerFasstoShopPort;

    @Override
    public RegisterFasstoShopResult registerShop(RegisterFasstoShopCommand command) {
        return registerFasstoShopPort.registerShop(
                FasstoShopCommandToRequestMapper.mapToRegisterRequest(command)
        );
    }
}
