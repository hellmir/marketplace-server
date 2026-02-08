package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoSettlementCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoSettlementDailyCostsCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoSettlementDailyCostsResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoSettlementDailyCostsUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoSettlementDailyCostsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoSettlementDailyCostsService implements GetFasstoSettlementDailyCostsUseCase {
    private final GetFasstoSettlementDailyCostsPort getFasstoSettlementDailyCostsPort;

    @Override
    public GetFasstoSettlementDailyCostsResult getDailyCosts(GetFasstoSettlementDailyCostsCommand command) {
        return getFasstoSettlementDailyCostsPort.getDailyCosts(
                FasstoSettlementCommandToRequestMapper.mapToQuery(command)
        );
    }
}
