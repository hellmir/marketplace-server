package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoGoodsCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoGoodsCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.RegisterFasstoGoodsResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RegisterFasstoGoodsUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.RegisterFasstoGoodsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class RegisterFasstoGoodsService implements RegisterFasstoGoodsUseCase {
    private final RegisterFasstoGoodsPort registerFasstoGoodsPort;

    @Override
    public RegisterFasstoGoodsResult registerGoods(RegisterFasstoGoodsCommand command) {
        return registerFasstoGoodsPort.registerGoods(
                FasstoGoodsCommandToRequestMapper.mapToRegisterRequest(command)
        );
    }
}
