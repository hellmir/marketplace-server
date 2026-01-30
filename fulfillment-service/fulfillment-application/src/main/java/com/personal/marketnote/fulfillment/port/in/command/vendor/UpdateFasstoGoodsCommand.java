package com.personal.marketnote.fulfillment.port.in.command.vendor;

import java.util.List;

public record UpdateFasstoGoodsCommand(
        String customerCode,
        String accessToken,
        List<UpdateFasstoGoodsItemCommand> goods
) {
    public static UpdateFasstoGoodsCommand of(
            String customerCode,
            String accessToken,
            List<UpdateFasstoGoodsItemCommand> goods
    ) {
        return new UpdateFasstoGoodsCommand(customerCode, accessToken, goods);
    }
}
