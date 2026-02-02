package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoWarehousingCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoWarehousingUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoWarehousingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoWarehousingService implements GetFasstoWarehousingUseCase {
    private final GetFasstoWarehousingPort getFasstoWarehousingPort;

    @Override
    public GetFasstoWarehousingResult getWarehousing(GetFasstoWarehousingCommand command) {
        return getFasstoWarehousingPort.getWarehousing(
                FasstoWarehousingCommandToRequestMapper.mapToQuery(command)
        );
    }
}
