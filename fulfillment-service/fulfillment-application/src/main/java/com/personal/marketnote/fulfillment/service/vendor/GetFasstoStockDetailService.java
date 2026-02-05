package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoStockCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoStockDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoStocksResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoStockDetailUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoStockDetailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoStockDetailService implements GetFasstoStockDetailUseCase {
    private final GetFasstoStockDetailPort getFasstoStockDetailPort;

    @Override
    public GetFasstoStocksResult getStockDetail(GetFasstoStockDetailCommand command) {
        return getFasstoStockDetailPort.getStockDetail(
                FasstoStockCommandToRequestMapper.mapToDetailQuery(command)
        );
    }
}
