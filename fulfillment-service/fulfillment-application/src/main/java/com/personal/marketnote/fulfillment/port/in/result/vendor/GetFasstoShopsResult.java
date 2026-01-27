package com.personal.marketnote.fulfillment.port.in.result.vendor;

import java.util.List;

public record GetFasstoShopsResult(
        Integer dataCount,
        List<FasstoShopInfoResult> shops
) {
    public static GetFasstoShopsResult of(
            Integer dataCount,
            List<FasstoShopInfoResult> shops
    ) {
        return new GetFasstoShopsResult(dataCount, shops);
    }
}
