package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.shop.FasstoShopMapper;
import com.personal.marketnote.fulfillment.domain.vendor.fassto.shop.FasstoShopQuery;
import com.personal.marketnote.fulfillment.port.in.command.vendor.GetFasstoShopsCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoShopCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.UpdateFasstoShopCommand;

public class FasstoShopCommandToRequestMapper {
    public static FasstoShopMapper mapToRegisterRequest(RegisterFasstoShopCommand command) {
        return FasstoShopMapper.register(
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

    public static FasstoShopQuery mapToShopsQuery(GetFasstoShopsCommand command) {
        return FasstoShopQuery.of(
                command.customerCode(),
                command.accessToken()
        );
    }

    public static FasstoShopMapper mapToUpdateRequest(UpdateFasstoShopCommand command) {
        return FasstoShopMapper.update(
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
