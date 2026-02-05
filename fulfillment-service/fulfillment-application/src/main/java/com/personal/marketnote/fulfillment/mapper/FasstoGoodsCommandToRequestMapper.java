package com.personal.marketnote.fulfillment.mapper;

import com.personal.marketnote.fulfillment.domain.vendor.fassto.goods.*;
import com.personal.marketnote.fulfillment.port.in.command.vendor.*;

import java.util.List;

public class FasstoGoodsCommandToRequestMapper {
    public static FasstoGoodsMapper mapToRegisterRequest(RegisterFasstoGoodsCommand command) {
        List<FasstoGoodsItemMapper> goods = command.goods().stream()
                .map(FasstoGoodsCommandToRequestMapper::mapItem)
                .toList();

        return FasstoGoodsMapper.register(
                command.customerCode(),
                command.accessToken(),
                goods
        );
    }

    public static FasstoGoodsQuery mapToGoodsQuery(GetFasstoGoodsCommand command) {
        return FasstoGoodsQuery.of(
                command.customerCode(),
                command.accessToken()
        );
    }

    public static FasstoGoodsDetailQuery mapToGoodsDetailQuery(GetFasstoGoodsDetailCommand command) {
        return FasstoGoodsDetailQuery.of(
                command.customerCode(),
                command.accessToken(),
                command.godNm()
        );
    }

    public static FasstoGoodsElementQuery mapToGoodsElementsQuery(GetFasstoGoodsElementsCommand command) {
        return FasstoGoodsElementQuery.of(
                command.customerCode(),
                command.accessToken()
        );
    }

    public static FasstoGoodsMapper mapToUpdateRequest(UpdateFasstoGoodsCommand command) {
        List<FasstoGoodsItemMapper> goods = command.goods().stream()
                .map(FasstoGoodsCommandToRequestMapper::mapItem)
                .toList();

        return FasstoGoodsMapper.register(
                command.customerCode(),
                command.accessToken(),
                goods
        );
    }

    private static FasstoGoodsItemMapper mapItem(RegisterFasstoGoodsItemCommand item) {
        return FasstoGoodsItemMapper.of(
                item.cstGodCd(),
                item.godNm(),
                item.godType(),
                item.giftDiv(),
                item.godOptCd1(),
                item.godOptCd2(),
                item.invGodNmUseYn(),
                item.invGodNm(),
                item.supCd(),
                item.cateCd(),
                item.seasonCd(),
                item.genderCd(),
                item.makeYr(),
                item.godPr(),
                item.inPr(),
                item.salPr(),
                item.dealTemp(),
                item.pickFac(),
                item.godBarcd(),
                item.boxWeight(),
                item.origin(),
                item.distTermMgtYn(),
                item.useTermDay(),
                item.outCanDay(),
                item.inCanDay(),
                item.boxDiv(),
                item.bufGodYn(),
                item.loadingDirection(),
                item.subMate(),
                item.useYn(),
                item.safetyStock(),
                item.feeYn(),
                item.saleUnitQty(),
                item.cstGodImgUrl(),
                item.externalGodImgUrl()
        );
    }

    private static FasstoGoodsItemMapper mapItem(UpdateFasstoGoodsItemCommand item) {
        return FasstoGoodsItemMapper.of(
                item.cstGodCd(),
                item.godNm(),
                item.godType(),
                item.giftDiv(),
                item.godOptCd1(),
                item.godOptCd2(),
                item.invGodNmUseYn(),
                item.invGodNm(),
                item.supCd(),
                item.cateCd(),
                item.seasonCd(),
                item.genderCd(),
                item.makeYr(),
                item.godPr(),
                item.inPr(),
                item.salPr(),
                item.dealTemp(),
                item.pickFac(),
                item.godBarcd(),
                item.boxWeight(),
                item.origin(),
                item.distTermMgtYn(),
                item.useTermDay(),
                item.outCanDay(),
                item.inCanDay(),
                item.boxDiv(),
                item.bufGodYn(),
                item.loadingDirection(),
                item.subMate(),
                item.useYn(),
                item.safetyStock(),
                item.feeYn(),
                item.saleUnitQty(),
                item.cstGodImgUrl(),
                item.externalGodImgUrl()
        );
    }
}
