package com.personal.marketnote.fulfillment.port.in.command.vendor;

import java.util.List;

public record RegisterFasstoGoodsCommand(
        String customerCode,
        String accessToken,
        List<RegisterFasstoGoodsItemCommand> goods
) {
    public static RegisterFasstoGoodsCommand of(
            String customerCode,
            String accessToken,
            List<RegisterFasstoGoodsItemCommand> goods
    ) {
        return new RegisterFasstoGoodsCommand(customerCode, accessToken, goods);
    }
}
