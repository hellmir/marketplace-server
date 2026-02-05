package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record UpdateFasstoWarehousingGoodsCommand(
        String cstGodCd,
        String distTermDt,
        Integer ordQty
) {
    public static UpdateFasstoWarehousingGoodsCommand of(
            String cstGodCd,
            String distTermDt,
            Integer ordQty
    ) {
        return new UpdateFasstoWarehousingGoodsCommand(cstGodCd, distTermDt, ordQty);
    }
}
