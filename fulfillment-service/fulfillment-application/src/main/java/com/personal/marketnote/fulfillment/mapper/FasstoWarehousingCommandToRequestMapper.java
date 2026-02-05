package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingGoodsMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingItemMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehousing.FasstoWarehousingQuery;
import com.personal.marketnote.fulfillment.port.in.command.vendor.*;

import java.util.List;

public class FasstoWarehousingCommandToRequestMapper {
    public static FasstoWarehousingMapper mapToRegisterRequest(RegisterFasstoWarehousingCommand command) {
        List<FasstoWarehousingItemMapper> requests = command.warehousingRequests().stream()
                .map(FasstoWarehousingCommandToRequestMapper::mapItem)
                .toList();

        return FasstoWarehousingMapper.register(
                command.customerCode(),
                command.accessToken(),
                requests
        );
    }

    public static FasstoWarehousingQuery mapToQuery(GetFasstoWarehousingCommand command) {
        return FasstoWarehousingQuery.of(
                command.customerCode(),
                command.accessToken(),
                command.startDate(),
                command.endDate()
        );
    }

    public static FasstoWarehousingMapper mapToUpdateRequest(UpdateFasstoWarehousingCommand command) {
        List<FasstoWarehousingItemMapper> requests = command.warehousingRequests().stream()
                .map(FasstoWarehousingCommandToRequestMapper::mapUpdateItem)
                .toList();

        return FasstoWarehousingMapper.update(
                command.customerCode(),
                command.accessToken(),
                requests
        );
    }

    private static FasstoWarehousingItemMapper mapItem(RegisterFasstoWarehousingItemCommand item) {
        List<FasstoWarehousingGoodsMapper> goods = item.godCds().stream()
                .map(FasstoWarehousingCommandToRequestMapper::mapGoods)
                .toList();

        return FasstoWarehousingItemMapper.of(
                item.ordDt(),
                item.ordNo(),
                item.inWay(),
                item.slipNo(),
                item.parcelComp(),
                item.parcelInvoiceNo(),
                item.remark(),
                item.cstSupCd(),
                item.distTermDt(),
                item.makeDt(),
                item.preArv(),
                goods
        );
    }

    private static FasstoWarehousingItemMapper mapUpdateItem(UpdateFasstoWarehousingItemCommand item) {
        List<FasstoWarehousingGoodsMapper> goods = item.godCds().stream()
                .map(FasstoWarehousingCommandToRequestMapper::mapUpdateGoods)
                .toList();

        return FasstoWarehousingItemMapper.update(
                item.ordDt(),
                item.ordNo(),
                item.inWay(),
                item.slipNo(),
                item.parcelComp(),
                item.parcelInvoiceNo(),
                item.remark(),
                item.cstSupCd(),
                item.distTermDt(),
                item.makeDt(),
                item.preArv(),
                goods
        );
    }

    private static FasstoWarehousingGoodsMapper mapGoods(RegisterFasstoWarehousingGoodsCommand item) {
        return FasstoWarehousingGoodsMapper.of(
                item.cstGodCd(),
                item.distTermDt(),
                item.ordQty()
        );
    }

    private static FasstoWarehousingGoodsMapper mapUpdateGoods(UpdateFasstoWarehousingGoodsCommand item) {
        return FasstoWarehousingGoodsMapper.of(
                item.cstGodCd(),
                item.distTermDt(),
                item.ordQty()
        );
    }
}
