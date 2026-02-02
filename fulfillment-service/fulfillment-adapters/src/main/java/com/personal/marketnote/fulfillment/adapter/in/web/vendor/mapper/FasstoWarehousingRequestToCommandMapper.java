package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoWarehousingGoodsRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoWarehousingRequest;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingGoodsCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehousingItemCommand;

import java.util.List;

public class FasstoWarehousingRequestToCommandMapper {
    public static RegisterFasstoWarehousingCommand mapToRegisterCommand(
            String customerCode,
            String accessToken,
            List<RegisterFasstoWarehousingRequest> request
    ) {
        List<RegisterFasstoWarehousingItemCommand> warehousingRequests = request.stream()
                .map(FasstoWarehousingRequestToCommandMapper::mapItem)
                .toList();

        return RegisterFasstoWarehousingCommand.of(customerCode, accessToken, warehousingRequests);
    }

    public static GetFasstoWarehousingCommand mapToWarehousingQuery(
            String customerCode,
            String accessToken,
            String startDate,
            String endDate
    ) {
        return GetFasstoWarehousingCommand.of(customerCode, accessToken, startDate, endDate);
    }

    private static RegisterFasstoWarehousingItemCommand mapItem(RegisterFasstoWarehousingRequest item) {
        List<RegisterFasstoWarehousingGoodsCommand> goods = item.getGodCds().stream()
                .map(FasstoWarehousingRequestToCommandMapper::mapGoods)
                .toList();

        return RegisterFasstoWarehousingItemCommand.of(
                item.getOrdDt(),
                item.getOrdNo(),
                item.getInWay(),
                item.getSlipNo(),
                item.getParcelComp(),
                item.getParcelInvoiceNo(),
                item.getRemark(),
                item.getCstSupCd(),
                item.getDistTermDt(),
                item.getMakeDt(),
                item.getPreArv(),
                goods
        );
    }

    private static RegisterFasstoWarehousingGoodsCommand mapGoods(RegisterFasstoWarehousingGoodsRequest item) {
        return RegisterFasstoWarehousingGoodsCommand.of(
                item.getCstGodCd(),
                item.getDistTermDt(),
                item.getOrdQty()
        );
    }
}
