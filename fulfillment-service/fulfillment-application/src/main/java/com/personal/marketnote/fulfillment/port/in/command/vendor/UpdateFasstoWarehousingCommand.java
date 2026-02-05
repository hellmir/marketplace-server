package com.personal.marketnote.fulfillment.port.in.command.vendor;

import java.util.List;

public record UpdateFasstoWarehousingCommand(
        String customerCode,
        String accessToken,
        List<UpdateFasstoWarehousingItemCommand> warehousingRequests
) {
    public static UpdateFasstoWarehousingCommand of(
            String customerCode,
            String accessToken,
            List<UpdateFasstoWarehousingItemCommand> warehousingRequests
    ) {
        return new UpdateFasstoWarehousingCommand(customerCode, accessToken, warehousingRequests);
    }
}
