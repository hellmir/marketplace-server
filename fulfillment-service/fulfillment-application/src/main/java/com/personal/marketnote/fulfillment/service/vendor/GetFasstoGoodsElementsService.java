package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoGoodsCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoGoodsElementsCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoGoodsElementsResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoGoodsElementsUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoGoodsElementsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoGoodsElementsService implements GetFasstoGoodsElementsUseCase {
    private final GetFasstoGoodsElementsPort getFasstoGoodsElementsPort;

    @Override
    public GetFasstoGoodsElementsResult getGoodsElements(GetFasstoGoodsElementsCommand command) {
        return getFasstoGoodsElementsPort.getGoodsElements(
                FasstoGoodsCommandToRequestMapper.mapToGoodsElementsQuery(command)
        );
    }
}
