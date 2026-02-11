package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoDeliveryCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoDeliveriesCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveriesResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoDeliveriesUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoDeliveriesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoDeliveriesService implements GetFasstoDeliveriesUseCase {
    private final GetFasstoDeliveriesPort getFasstoDeliveriesPort;

    @Override
    public GetFasstoDeliveriesResult getDeliveries(GetFasstoDeliveriesCommand command) {
        return getFasstoDeliveriesPort.getDeliveries(
                FasstoDeliveryCommandToRequestMapper.mapToQuery(command)
        );
    }
}
