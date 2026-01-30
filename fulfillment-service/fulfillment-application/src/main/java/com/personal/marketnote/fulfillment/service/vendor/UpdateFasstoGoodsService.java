package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoGoodsCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoGoodsCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.UpdateFasstoGoodsResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.UpdateFasstoGoodsUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.UpdateFasstoGoodsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class UpdateFasstoGoodsService implements UpdateFasstoGoodsUseCase {
    private final UpdateFasstoGoodsPort updateFasstoGoodsPort;

    @Override
    public UpdateFasstoGoodsResult updateGoods(UpdateFasstoGoodsCommand command) {
        return updateFasstoGoodsPort.updateGoods(
                FasstoGoodsCommandToRequestMapper.mapToUpdateRequest(command)
        );
    }
}
