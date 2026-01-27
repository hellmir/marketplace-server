package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoShopRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.UpdateFasstoShopRequest;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoShopsCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoShopCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoShopCommand;

public class FasstoShopRequestToCommandMapper {
    public static RegisterFasstoShopCommand mapToRegisterCommand(
            String customerCode,
            String accessToken,
            RegisterFasstoShopRequest request
    ) {
        return RegisterFasstoShopCommand.of(
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

    public static GetFasstoShopsCommand mapToShopsCommand(
            String customerCode,
            String accessToken
    ) {
        return GetFasstoShopsCommand.of(customerCode, accessToken);
    }

    public static UpdateFasstoShopCommand mapToUpdateCommand(
            String customerCode,
            String accessToken,
            UpdateFasstoShopRequest request
    ) {
        return UpdateFasstoShopCommand.of(
                customerCode,
                accessToken,
                request.getShopCd(),
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
