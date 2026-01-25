package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.supplier.FasstoSupplierMapper;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoSupplierCommand;

public class FasstoSupplierCommandToRequestMapper {
    public static FasstoSupplierMapper mapToRegisterRequest(RegisterFasstoSupplierCommand command) {
        return FasstoSupplierMapper.register(
                command.customerCode(),
                command.accessToken(),
                command.supNm(),
                command.cstSupCd(),
                command.useYn(),
                command.dealStrDt(),
                command.dealEndDt(),
                command.zipNo(),
                command.addr1(),
                command.addr2(),
                command.ceoNm(),
                command.busNo(),
                command.busSp(),
                command.busTp(),
                command.telNo(),
                command.faxNo(),
                command.empNm1(),
                command.empPosit1(),
                command.empTelNo1(),
                command.empEmail1(),
                command.empNm2(),
                command.empPosit2(),
                command.empTelNo2(),
                command.empEmail2()
        );
    }
}
