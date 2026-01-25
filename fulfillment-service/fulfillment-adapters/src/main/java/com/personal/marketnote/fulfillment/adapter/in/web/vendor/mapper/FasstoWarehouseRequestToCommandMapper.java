package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoWarehouseRequest;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehouseCommand;

public class FasstoWarehouseRequestToCommandMapper {
    public static RegisterFasstoWarehouseCommand mapToRegisterCommand(
            String customerCode,
            String accessToken,
            RegisterFasstoWarehouseRequest request
    ) {
        return RegisterFasstoWarehouseCommand.of(
                customerCode,
                accessToken,
                request.getShopNm(),
                request.getCstShopCd(),
                request.getDealStrDt(),
                request.getDealEndDt(),
                request.getZipNo(),
                request.getAddr1(),
                request.getAddr2(),
                request.getCeoNm(),
                request.getBusNo(),
                request.getTelNo(),
                request.getUnloadWay(),
                request.getCheckWay(),
                request.getStandYn(),
                request.getFormType(),
                request.getEmpNm(),
                request.getEmpPosit(),
                request.getEmpTelNo(),
                request.getUseYn()
        );
    }
}
