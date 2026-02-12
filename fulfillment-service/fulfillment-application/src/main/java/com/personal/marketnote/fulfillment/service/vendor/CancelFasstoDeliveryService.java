package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoDeliveryCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.CancelFasstoDeliveryCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.CancelFasstoDeliveryResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.CancelFasstoDeliveryUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.CancelFasstoDeliveryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class CancelFasstoDeliveryService implements CancelFasstoDeliveryUseCase {
    private final CancelFasstoDeliveryPort cancelFasstoDeliveryPort;

    @Override
    public CancelFasstoDeliveryResult cancelDelivery(CancelFasstoDeliveryCommand command) {
        return cancelFasstoDeliveryPort.cancelDelivery(
                FasstoDeliveryCommandToRequestMapper.mapToCancelRequest(command)
        );
    }
}
