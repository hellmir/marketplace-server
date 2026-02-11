package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoDeliveryCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoDeliveryDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryDetailResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoDeliveryDetailUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoDeliveryDetailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoDeliveryDetailService implements GetFasstoDeliveryDetailUseCase {
    private final GetFasstoDeliveryDetailPort getFasstoDeliveryDetailPort;

    @Override
    public GetFasstoDeliveryDetailResult getDeliveryDetail(GetFasstoDeliveryDetailCommand command) {
        return getFasstoDeliveryDetailPort.getDeliveryDetail(
                FasstoDeliveryCommandToRequestMapper.mapToDetailQuery(command)
        );
    }
}
