package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoGoodsCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoGoodsDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoGoodsResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoGoodsDetailUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoGoodsDetailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoGoodsDetailService implements GetFasstoGoodsDetailUseCase {
    private final GetFasstoGoodsDetailPort getFasstoGoodsDetailPort;

    @Override
    public GetFasstoGoodsResult getGoodsDetail(GetFasstoGoodsDetailCommand command) {
        return getFasstoGoodsDetailPort.getGoodsDetail(
                FasstoGoodsCommandToRequestMapper.mapToGoodsDetailQuery(command)
        );
    }
}
