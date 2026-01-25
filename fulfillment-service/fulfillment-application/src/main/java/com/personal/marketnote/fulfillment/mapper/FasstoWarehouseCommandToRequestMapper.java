package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehouse.FasstoWarehouseMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.warehouse.FasstoWarehouseQuery;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoWarehousesCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoWarehouseCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoWarehouseCommand;

public class FasstoWarehouseCommandToRequestMapper {
    public static FasstoWarehouseMapper mapToRegisterRequest(RegisterFasstoWarehouseCommand command) {
        return FasstoWarehouseMapper.register(
                command.customerCode(),
                command.accessToken(),
                command.shopNm(),
                command.cstShopCd(),
                command.dealStrDt(),
                command.dealEndDt(),
                command.zipNo(),
                command.addr1(),
                command.addr2(),
                command.ceoNm(),
                command.busNo(),
                command.telNo(),
                command.unloadWay(),
                command.checkWay(),
                command.standYn(),
                command.formType(),
                command.empNm(),
                command.empPosit(),
                command.empTelNo(),
                command.useYn()
        );
    }

    public static FasstoWarehouseQuery mapToWarehousesQuery(GetFasstoWarehousesCommand command) {
        return FasstoWarehouseQuery.of(
                command.customerCode(),
                command.accessToken()
        );
    }

    public static FasstoWarehouseMapper mapToUpdateRequest(UpdateFasstoWarehouseCommand command) {
        return FasstoWarehouseMapper.update(
                command.customerCode(),
                command.accessToken(),
                command.shopCd(),
                command.shopNm(),
                command.cstShopCd(),
                command.dealStrDt(),
                command.dealEndDt(),
                command.zipNo(),
                command.addr1(),
                command.addr2(),
                command.ceoNm(),
                command.busNo(),
                command.telNo(),
                command.unloadWay(),
                command.checkWay(),
                command.standYn(),
                command.formType(),
                command.empNm(),
                command.empPosit(),
                command.empTelNo(),
                command.useYn()
        );
    }
}
