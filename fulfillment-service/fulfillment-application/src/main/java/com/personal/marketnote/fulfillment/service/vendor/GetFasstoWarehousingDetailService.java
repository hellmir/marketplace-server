package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoWarehousingCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousingDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoWarehousingDetailResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoWarehousingDetailUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoWarehousingDetailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoWarehousingDetailService implements GetFasstoWarehousingDetailUseCase {
    private final GetFasstoWarehousingDetailPort getFasstoWarehousingDetailPort;

    @Override
    public GetFasstoWarehousingDetailResult getWarehousingDetail(GetFasstoWarehousingDetailCommand command) {
        return getFasstoWarehousingDetailPort.getWarehousingDetail(
                FasstoWarehousingCommandToRequestMapper.mapToDetailQuery(command)
        );
    }
}
