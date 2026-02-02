package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record RegisterFasstoWarehousingGoodsCommand(
        String cstGodCd,
        String distTermDt,
        Integer ordQty
) {
    public static RegisterFasstoWarehousingGoodsCommand of(
            String cstGodCd,
            String distTermDt,
            Integer ordQty
    ) {
        return new RegisterFasstoWarehousingGoodsCommand(cstGodCd, distTermDt, ordQty);
    }
}
