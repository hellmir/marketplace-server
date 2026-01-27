package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoShopCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoShopCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoShopResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.UpdateFasstoShopUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.UpdateFasstoShopPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class UpdateFasstoShopService implements UpdateFasstoShopUseCase {
    private final UpdateFasstoShopPort updateFasstoShopPort;

    @Override
    public UpdateFasstoShopResult updateShop(UpdateFasstoShopCommand command) {
        return updateFasstoShopPort.updateShop(
                FasstoShopCommandToRequestMapper.mapToUpdateRequest(command)
        );
    }
}
