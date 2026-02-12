package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoDeliveryOutOrdGoodsDetailCommand(
        String customerCode,
        String accessToken,
        String outOrdSlipNo
) {
    public static GetFasstoDeliveryOutOrdGoodsDetailCommand of(
            String customerCode,
            String accessToken,
            String outOrdSlipNo
    ) {
        return new GetFasstoDeliveryOutOrdGoodsDetailCommand(customerCode, accessToken, outOrdSlipNo);
    }
}
