package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoGoodsCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoGoodsCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoGoodsResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoGoodsUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoGoodsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoGoodsService implements GetFasstoGoodsUseCase {
    private final GetFasstoGoodsPort getFasstoGoodsPort;

    @Override
    public GetFasstoGoodsResult getGoods(GetFasstoGoodsCommand command) {
        return getFasstoGoodsPort.getGoods(
                FasstoGoodsCommandToRequestMapper.mapToGoodsQuery(command)
        );
    }
}
