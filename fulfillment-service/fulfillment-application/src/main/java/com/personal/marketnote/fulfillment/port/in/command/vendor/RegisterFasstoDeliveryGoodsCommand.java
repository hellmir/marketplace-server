package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record RegisterFasstoDeliveryGoodsCommand(
        String cstGodCd,
        String distTermDt,
        Integer ordQty
) {
    public static RegisterFasstoDeliveryGoodsCommand of(
            String cstGodCd,
            String distTermDt,
            Integer ordQty
    ) {
        return new RegisterFasstoDeliveryGoodsCommand(cstGodCd, distTermDt, ordQty);
    }
}
