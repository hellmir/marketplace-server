package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoSupplierRequest;
import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.UpdateFasstoSupplierRequest;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoSuppliersCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoSupplierCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoSupplierCommand;

public class FasstoSupplierRequestToCommandMapper {
    public static RegisterFasstoSupplierCommand mapToRegisterCommand(
            String customerCode,
            String accessToken,
            RegisterFasstoSupplierRequest request
    ) {
        return RegisterFasstoSupplierCommand.of(
                customerCode,
                accessToken,
                request.getSupNm(),
                request.getCstSupCd(),
                request.getUseYn(),
                request.getDealStrDt(),
                request.getDealEndDt(),
                request.getZipNo(),
                request.getAddr1(),
                request.getAddr2(),
                request.getCeoNm(),
                request.getBusNo(),
                request.getBusSp(),
                request.getBusTp(),
                request.getTelNo(),
                request.getFaxNo(),
                request.getEmpNm1(),
                request.getEmpPosit1(),
                request.getEmpTelNo1(),
                request.getEmpEmail1(),
                request.getEmpNm2(),
                request.getEmpPosit2(),
                request.getEmpTelNo2(),
                request.getEmpEmail2()
        );
    }

    public static GetFasstoSuppliersCommand mapToSuppliersCommand(
            String customerCode,
            String accessToken
    ) {
        return GetFasstoSuppliersCommand.of(customerCode, accessToken);
    }

    public static UpdateFasstoSupplierCommand mapToUpdateCommand(
            String customerCode,
            String accessToken,
            UpdateFasstoSupplierRequest request
    ) {
        return UpdateFasstoSupplierCommand.of(
                customerCode,
                accessToken,
                request.getSupCd(),
                request.getSupNm(),
                request.getCstSupCd(),
                request.getUseYn(),
                request.getDealStrDt(),
                request.getDealEndDt(),
                request.getZipNo(),
                request.getAddr1(),
                request.getAddr2(),
                request.getCeoNm(),
                request.getBusNo(),
                request.getBusSp(),
                request.getBusTp(),
                request.getTelNo(),
                request.getFaxNo(),
                request.getEmpNm1(),
                request.getEmpPosit1(),
                request.getEmpTelNo1(),
                request.getEmpEmail1(),
                request.getEmpNm2(),
                request.getEmpPosit2(),
                request.getEmpTelNo2(),
                request.getEmpEmail2()
        );
    }
}
