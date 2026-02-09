package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.fulfillment.mapper.FasstoWarehousingCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingItemCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoWarehousingResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoWarehousingUseCase;
import com.personal.marketnote.fulfillment.port.out.scheduler.ScheduleFasstoWarehousingPollingCommand;
import com.personal.marketnote.fulfillment.port.out.scheduler.ScheduleFasstoWarehousingPollingPort;
import com.personal.marketnote.fulfillment.port.out.vendor.RegisterFasstoWarehousingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class RegisterFasstoWarehousingService implements RegisterFasstoWarehousingUseCase {
    private final RegisterFasstoWarehousingPort registerFasstoWarehousingPort;
    private final ScheduleFasstoWarehousingPollingPort scheduleFasstoWarehousingPollingPort;

    @Override
    public RegisterFasstoWarehousingResult registerWarehousing(RegisterFasstoWarehousingCommand command) {
        RegisterFasstoWarehousingResult result = registerFasstoWarehousingPort.registerWarehousing(
                FasstoWarehousingCommandToRequestMapper.mapToRegisterRequest(command)
        );
        schedulePolling(command, result);
        return result;
    }

    private void schedulePolling(RegisterFasstoWarehousingCommand command, RegisterFasstoWarehousingResult result) {
        if (FormatValidator.hasNoValue(command)
                || FormatValidator.hasNoValue(command.warehousingRequests())
                || FormatValidator.hasNoValue(result)) {
            return;
        }

        for (RegisterFasstoWarehousingItemCommand item : command.warehousingRequests()) {
            if (FormatValidator.hasNoValue(item) || FormatValidator.hasNoValue(item.ordNo())) {
                continue;
            }

            scheduleFasstoWarehousingPollingPort.schedule(
                    ScheduleFasstoWarehousingPollingCommand.of(
                            command.customerCode(),
                            item.ordNo(),
                            item.ordDt()
                    )
            );
        }
    }
}
