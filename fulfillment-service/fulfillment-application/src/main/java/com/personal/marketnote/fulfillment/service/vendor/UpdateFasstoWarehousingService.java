package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoWarehousingCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoWarehousingResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.UpdateFasstoWarehousingUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.UpdateFasstoWarehousingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class UpdateFasstoWarehousingService implements UpdateFasstoWarehousingUseCase {
    private final UpdateFasstoWarehousingPort updateFasstoWarehousingPort;

    @Override
    public UpdateFasstoWarehousingResult updateWarehousing(UpdateFasstoWarehousingCommand command) {
        return updateFasstoWarehousingPort.updateWarehousing(
                FasstoWarehousingCommandToRequestMapper.mapToUpdateRequest(command)
        );
    }
}
