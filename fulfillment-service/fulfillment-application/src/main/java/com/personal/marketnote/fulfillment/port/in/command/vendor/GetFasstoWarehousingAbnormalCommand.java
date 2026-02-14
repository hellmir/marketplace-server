package com.personal.marketnote.fulfillment.port.in.command.vendor;

public record GetFasstoWarehousingAbnormalCommand(
        String customerCode,
        String accessToken,
        String whCd,
        String slipNo
) {
    public static GetFasstoWarehousingAbnormalCommand of(
            String customerCode,
            String accessToken,
            String whCd,
            String slipNo
    ) {
        return new GetFasstoWarehousingAbnormalCommand(customerCode, accessToken, whCd, slipNo);
    }
}
