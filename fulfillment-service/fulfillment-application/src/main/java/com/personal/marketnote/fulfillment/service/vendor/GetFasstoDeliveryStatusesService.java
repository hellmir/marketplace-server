package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoDeliveryCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoDeliveryStatusesCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryStatusesResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoDeliveryStatusesUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoDeliveryStatusesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoDeliveryStatusesService implements GetFasstoDeliveryStatusesUseCase {
    private final GetFasstoDeliveryStatusesPort getFasstoDeliveryStatusesPort;

    @Override
    public GetFasstoDeliveryStatusesResult getDeliveryStatuses(GetFasstoDeliveryStatusesCommand command) {
        return getFasstoDeliveryStatusesPort.getDeliveryStatuses(
                FasstoDeliveryCommandToRequestMapper.mapToDeliveryStatusQuery(command)
        );
    }
}
