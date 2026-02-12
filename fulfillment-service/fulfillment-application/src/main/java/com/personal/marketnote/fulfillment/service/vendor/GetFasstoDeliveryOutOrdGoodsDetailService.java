package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.mapper.FasstoDeliveryCommandToRequestMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoDeliveryOutOrdGoodsDetailCommand;
import com.personal.marketnote.fulfillment.port.in.result.vendor.GetFasstoDeliveryOutOrdGoodsDetailResult;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.GetFasstoDeliveryOutOrdGoodsDetailUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.GetFasstoDeliveryOutOrdGoodsDetailPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFasstoDeliveryOutOrdGoodsDetailService implements GetFasstoDeliveryOutOrdGoodsDetailUseCase {
    private final GetFasstoDeliveryOutOrdGoodsDetailPort getFasstoDeliveryOutOrdGoodsDetailPort;

    @Override
    public GetFasstoDeliveryOutOrdGoodsDetailResult getOutOrdGoodsDetail(GetFasstoDeliveryOutOrdGoodsDetailCommand command) {
        return getFasstoDeliveryOutOrdGoodsDetailPort.getOutOrdGoodsDetail(
                FasstoDeliveryCommandToRequestMapper.mapToOutOrdGoodsDetailQuery(command)
        );
    }
}
