package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoShopCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoShopsCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoShopsResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoShopsUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoShopsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoShopsService implements GetFasstoShopsUseCase {
    private final GetFasstoShopsPort getFasstoShopsPort;

    @Override
    public GetFasstoShopsResult getShops(GetFasstoShopsCommand command) {
        return getFasstoShopsPort.getShops(
                FasstoShopCommandToRequestMapper.mapToShopsQuery(command)
        );
    }
}
