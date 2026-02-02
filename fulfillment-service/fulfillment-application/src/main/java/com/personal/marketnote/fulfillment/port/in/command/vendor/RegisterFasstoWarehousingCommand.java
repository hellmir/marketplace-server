package com.personal.marketnote.fulfillment.port.in.command.vendor;

import java.util.List;

public record RegisterFasstoWarehousingCommand(
        String customerCode,
        String accessToken,
        List<RegisterFasstoWarehousingItemCommand> warehousingRequests
) {
    public static RegisterFasstoWarehousingCommand of(
            String customerCode,
            String accessToken,
            List<RegisterFasstoWarehousingItemCommand> warehousingRequests
    ) {
        return new RegisterFasstoWarehousingCommand(customerCode, accessToken, warehousingRequests);
    }
}
