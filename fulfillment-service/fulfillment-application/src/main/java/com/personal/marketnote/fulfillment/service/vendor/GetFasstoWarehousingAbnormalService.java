package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoWarehousingCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousingAbnormalCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingAbnormalResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoWarehousingAbnormalUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoWarehousingAbnormalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoWarehousingAbnormalService implements GetFasstoWarehousingAbnormalUseCase {
    private final GetFasstoWarehousingAbnormalPort getFasstoWarehousingAbnormalPort;

    @Override
    public GetFasstoWarehousingAbnormalResult getWarehousingAbnormal(GetFasstoWarehousingAbnormalCommand command) {
        return getFasstoWarehousingAbnormalPort.getWarehousingAbnormal(
                FasstoWarehousingCommandToRequestMapper.mapToAbnormalQuery(command)
        );
    }
}
