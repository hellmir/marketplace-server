package com.personal.marketnote.product.mapper;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.product.domain.product.Product;
import com.personal.marketnote.product.exception.ProductInfoNoValueException;
import com.personal.marketnote.product.port.in.command.FulfillmentVendorGoodsOptionCommand;
import com.personal.marketnote.product.port.out.fulfillment.RegisterFulfillmentVendorGoodsCommand;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.*;

public class FulfillmentVendorGoodsCommandMapper {
    private static final String DEFAULT_GOD_TYPE = "1";
    private static final String DEFAULT_GIFT_DIV = "01";

    public static RegisterFulfillmentVendorGoodsCommand mapToRegisterCommand(
            Product product, FulfillmentVendorGoodsOptionCommand options
    ) {
        if (FormatValidator.hasNoValue(product)) {
            throw new ProductInfoNoValueException("%s:: 상품이 존재하지 않습니다.", FIRST_ERROR_CODE);
        }
        if (FormatValidator.hasNoValue(product.getId())) {
            throw new ProductInfoNoValueException("%s:: 상품 ID가 존재하지 않습니다.", SECOND_ERROR_CODE);
        }
        if (FormatValidator.hasNoValue(product.getName())) {
            throw new ProductInfoNoValueException("%s:: 상품명이 존재하지 않습니다.", THIRD_ERROR_CODE);
        }

        if (FormatValidator.hasNoValue(options)) {
            return RegisterFulfillmentVendorGoodsCommand.builder()
                    .cstGodCd(String.valueOf(product.getId()))
                    .godNm(product.getName())
                    .godType(DEFAULT_GOD_TYPE)
                    .giftDiv(DEFAULT_GIFT_DIV)
                    .build();
        }

        return RegisterFulfillmentVendorGoodsCommand.builder()
                .cstGodCd(String.valueOf(product.getId()))
                .godNm(product.getName())
                .godType(options.godType())
                .giftDiv(options.giftDiv())
                .godOptCd1(options.godOptCd1())
                .godOptCd2(options.godOptCd2())
                .invGodNmUseYn(options.invGodNmUseYn())
                .invGodNm(options.invGodNm())
                .supCd(options.supCd())
                .cateCd(options.cateCd())
                .seasonCd(options.seasonCd())
                .genderCd(options.genderCd())
                .makeYr(options.makeYr())
                .godPr(options.godPr())
                .inPr(options.inPr())
                .salPr(options.salPr())
                .dealTemp(options.dealTemp())
                .pickFac(options.pickFac())
                .godBarcd(options.godBarcd())
                .boxWeight(options.boxWeight())
                .origin(options.origin())
                .distTermMgtYn(options.distTermMgtYn())
                .useTermDay(options.useTermDay())
                .outCanDay(options.outCanDay())
                .inCanDay(options.inCanDay())
                .boxDiv(options.boxDiv())
                .bufGodYn(options.bufGodYn())
                .loadingDirection(options.loadingDirection())
                .subMate(options.subMate())
                .useYn(options.useYn())
                .safetyStock(options.safetyStock())
                .feeYn(options.feeYn())
                .saleUnitQty(options.saleUnitQty())
                .cstGodImgUrl(options.cstGodImgUrl())
                .externalGodImgUrl(options.externalGodImgUrl())
                .build();
    }
}
