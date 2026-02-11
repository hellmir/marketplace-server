package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery.FasstoDeliveryGoodsMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery.FasstoDeliveryItemMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery.FasstoDeliveryMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.delivery.FasstoDeliveryQuery;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoDeliveriesCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoDeliveryCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoDeliveryGoodsCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoDeliveryItemCommand;

import java.util.List;

public class FasstoDeliveryCommandToRequestMapper {
    public static FasstoDeliveryQuery mapToQuery(GetFasstoDeliveriesCommand command) {
        return FasstoDeliveryQuery.of(
                command.customerCode(),
                command.accessToken(),
                command.startDate(),
                command.endDate(),
                command.status(),
                command.outDiv(),
                command.ordNo()
        );
    }

    public static FasstoDeliveryMapper mapToRegisterRequest(RegisterFasstoDeliveryCommand command) {
        List<FasstoDeliveryItemMapper> deliveryRequests = command.deliveryRequests().stream()
                .map(FasstoDeliveryCommandToRequestMapper::mapItem)
                .toList();

        return FasstoDeliveryMapper.register(
                command.customerCode(),
                command.accessToken(),
                deliveryRequests
        );
    }

    private static FasstoDeliveryItemMapper mapItem(RegisterFasstoDeliveryItemCommand item) {
        List<FasstoDeliveryGoodsMapper> goods = item.godCds().stream()
                .map(FasstoDeliveryCommandToRequestMapper::mapGoods)
                .toList();

        return FasstoDeliveryItemMapper.of(
                item.ordDt(),
                item.ordNo(),
                item.ordSeq(),
                item.slipNo(),
                item.custNm(),
                item.custTelNo(),
                item.custAddr(),
                item.outWay(),
                item.sendNm(),
                item.sendTelNo(),
                item.salChanel(),
                item.shipReqTerm(),
                goods,
                item.oneDayDeliveryCd(),
                item.remark()
        );
    }

    private static FasstoDeliveryGoodsMapper mapGoods(RegisterFasstoDeliveryGoodsCommand item) {
        return FasstoDeliveryGoodsMapper.of(
                item.cstGodCd(),
                item.distTermDt(),
                item.ordQty()
        );
    }
}
