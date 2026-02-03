package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoStockCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStocksCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoStocksResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoStocksUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoStocksPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoStocksService implements GetFasstoStocksUseCase {
    private final GetFasstoStocksPort getFasstoStocksPort;

    @Override
    public GetFasstoStocksResult getStocks(GetFasstoStocksCommand command) {
        return getFasstoStocksPort.getStocks(
                FasstoStockCommandToRequestMapper.mapToQuery(command)
        );
    }
}
