package com.personal.marketnote.product.port.out.fulfillment;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.Builder;

@Builder
public record RegisterFulfillmentVendorGoodsCommand(
        String cstGodCd,
        String godNm,
        String godType,
        String giftDiv,
        String godOptCd1,
        String godOptCd2,
        String invGodNmUseYn,
        String invGodNm,
        String supCd,
        String cateCd,
        String seasonCd,
        String genderCd,
        String makeYr,
        String godPr,
        String inPr,
        String salPr,
        String dealTemp,
        String pickFac,
        String godBarcd,
        String boxWeight,
        String origin,
        String distTermMgtYn,
        String useTermDay,
        String outCanDay,
        String inCanDay,
        String boxDiv,
        String bufGodYn,
        String loadingDirection,
        String subMate,
        String useYn,
        String safetyStock,
        String feeYn,
        String saleUnitQty,
        String cstGodImgUrl,
        String externalGodImgUrl
) {
    public RegisterFulfillmentVendorGoodsCommand {
        if (FormatValidator.hasNoValue(cstGodCd)) {
            throw new IllegalArgumentException("cstGodCd is required for Fassto goods registration.");
        }
        if (FormatValidator.hasNoValue(godNm)) {
            throw new IllegalArgumentException("godNm is required for Fassto goods registration.");
        }
        if (FormatValidator.hasNoValue(godType)) {
            throw new IllegalArgumentException("godType is required for Fassto goods registration.");
        }
        if (FormatValidator.hasNoValue(giftDiv)) {
            throw new IllegalArgumentException("giftDiv is required for Fassto goods registration.");
        }
    }
}
