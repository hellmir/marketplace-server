package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoWarehousingDetailCommand(
        String customerCode,
        String accessToken,
        String slipNo,
        String ordNo
) {
    public static GetFasstoWarehousingDetailCommand of(
            String customerCode,
            String accessToken,
            String slipNo,
            String ordNo
    ) {
        return new GetFasstoWarehousingDetailCommand(customerCode, accessToken, slipNo, ordNo);
    }
}
