package com.personal.marketnote.fulfillment.adapter.in.web.vendor.mapper;

import com.personal.marketnote.fulfillment.adapter.in.web.vendor.request.RegisterFasstoGoodsRequest;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoGoodsCommand;
import com.personal.marketnote.fulfillment.port.in.command.vendor.RegisterFasstoGoodsItemCommand;

import java.util.List;

public class FasstoGoodsRequestToCommandMapper {
    public static RegisterFasstoGoodsCommand mapToRegisterCommand(
            String customerCode,
            String accessToken,
            List<RegisterFasstoGoodsRequest> request
    ) {
        List<RegisterFasstoGoodsItemCommand> goods = request.stream()
                .map(item -> RegisterFasstoGoodsItemCommand.of(
                        item.getCstGodCd(),
                        item.getGodNm(),
                        item.getGodType(),
                        item.getGiftDiv(),
                        item.getGodOptCd1(),
                        item.getGodOptCd2(),
                        item.getInvGodNmUseYn(),
                        item.getInvGodNm(),
                        item.getSupCd(),
                        item.getCateCd(),
                        item.getSeasonCd(),
                        item.getGenderCd(),
                        item.getMakeYr(),
                        item.getGodPr(),
                        item.getInPr(),
                        item.getSalPr(),
                        item.getDealTemp(),
                        item.getPickFac(),
                        item.getGodBarcd(),
                        item.getBoxWeight(),
                        item.getOrigin(),
                        item.getDistTermMgtYn(),
                        item.getUseTermDay(),
                        item.getOutCanDay(),
                        item.getInCanDay(),
                        item.getBoxDiv(),
                        item.getBufGodYn(),
                        item.getLoadingDirection(),
                        item.getSubMate(),
                        item.getUseYn(),
                        item.getSafetyStock(),
                        item.getFeeYn(),
                        item.getSaleUnitQty(),
                        item.getCstGodImgUrl(),
                        item.getExternalGodImgUrl()
                ))
                .toList();

        return RegisterFasstoGoodsCommand.of(customerCode, accessToken, goods);
    }
}
