package com.personal.marketnote.product.adapter.out.web.fulfillment.request;

import com.personal.marketnote.product.port.out.fulfillment.RegisterFulfillmentVendorGoodsCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterFasstoGoodsItemRequest {
    private String cstGodCd;
    private String godNm;
    private String godType;
    private String giftDiv;
    private String godOptCd1;
    private String godOptCd2;
    private String invGodNmUseYn;
    private String invGodNm;
    private String supCd;
    private String cateCd;
    private String seasonCd;
    private String genderCd;
    private String makeYr;
    private String godPr;
    private String inPr;
    private String salPr;
    private String dealTemp;
    private String pickFac;
    private String godBarcd;
    private String boxWeight;
    private String origin;
    private String distTermMgtYn;
    private String useTermDay;
    private String outCanDay;
    private String inCanDay;
    private String boxDiv;
    private String bufGodYn;
    private String loadingDirection;
    private String subMate;
    private String useYn;
    private String safetyStock;
    private String feeYn;
    private String saleUnitQty;
    private String cstGodImgUrl;
    private String externalGodImgUrl;

    public static RegisterFasstoGoodsItemRequest from(RegisterFulfillmentVendorGoodsCommand command) {
        return RegisterFasstoGoodsItemRequest.builder()
                .cstGodCd(command.cstGodCd())
                .godNm(command.godNm())
                .godType(command.godType())
                .giftDiv(command.giftDiv())
                .godOptCd1(command.godOptCd1())
                .godOptCd2(command.godOptCd2())
                .invGodNmUseYn(command.invGodNmUseYn())
                .invGodNm(command.invGodNm())
                .supCd(command.supCd())
                .cateCd(command.cateCd())
                .seasonCd(command.seasonCd())
                .genderCd(command.genderCd())
                .makeYr(command.makeYr())
                .godPr(command.godPr())
                .inPr(command.inPr())
                .salPr(command.salPr())
                .dealTemp(command.dealTemp())
                .pickFac(command.pickFac())
                .godBarcd(command.godBarcd())
                .boxWeight(command.boxWeight())
                .origin(command.origin())
                .distTermMgtYn(command.distTermMgtYn())
                .useTermDay(command.useTermDay())
                .outCanDay(command.outCanDay())
                .inCanDay(command.inCanDay())
                .boxDiv(command.boxDiv())
                .bufGodYn(command.bufGodYn())
                .loadingDirection(command.loadingDirection())
                .subMate(command.subMate())
                .useYn(command.useYn())
                .safetyStock(command.safetyStock())
                .feeYn(command.feeYn())
                .saleUnitQty(command.saleUnitQty())
                .cstGodImgUrl(command.cstGodImgUrl())
                .externalGodImgUrl(command.externalGodImgUrl())
                .build();
    }
}
