package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoDeliveryGoodsRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoDeliveryRequest;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoDeliveryCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoDeliveryGoodsCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoDeliveryItemCommand;

import java.util.List;

public class FasstoDeliveryRequestToCommandMapper {
    public static RegisterFasstoDeliveryCommand mapToRegisterCommand(
            String customerCode,
            String accessToken,
            List<RegisterFasstoDeliveryRequest> request
    ) {
        List<RegisterFasstoDeliveryItemCommand> deliveryRequests = request.stream()
                .map(FasstoDeliveryRequestToCommandMapper::mapItem)
                .toList();

        return RegisterFasstoDeliveryCommand.of(customerCode, accessToken, deliveryRequests);
    }

    private static RegisterFasstoDeliveryItemCommand mapItem(RegisterFasstoDeliveryRequest item) {
        List<RegisterFasstoDeliveryGoodsCommand> goods = item.getGodCds().stream()
                .map(FasstoDeliveryRequestToCommandMapper::mapGoods)
                .toList();

        return RegisterFasstoDeliveryItemCommand.builder()
                .ordDt(item.getOrdDt())
                .ordNo(item.getOrdNo())
                .ordSeq(item.getOrdSeq())
                .slipNo(item.getSlipNo())
                .custNm(item.getCustNm())
                .custTelNo(item.getCustTelNo())
                .custAddr(item.getCustAddr())
                .outWay(item.getOutWay())
                .sendNm(item.getSendNm())
                .sendTelNo(item.getSendTelNo())
                .salChanel(item.getSalChanel())
                .shipReqTerm(item.getShipReqTerm())
                .godCds(goods)
                .oneDayDeliveryCd(item.getOneDayDeliveryCd())
                .remark(item.getRemark())
                .build();
    }

    private static RegisterFasstoDeliveryGoodsCommand mapGoods(RegisterFasstoDeliveryGoodsRequest item) {
        return RegisterFasstoDeliveryGoodsCommand.of(
                item.getCstGodCd(),
                item.getDistTermDt(),
                item.getOrdQty()
        );
    }
}
