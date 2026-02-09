package com.personal.marketnote.fulfillment.port.out.scheduler;

public record ScheduleFasstoWarehousingPollingCommand(
        String customerCode,
        String ordNo,
        String ordDt
) {
    public static ScheduleFasstoWarehousingPollingCommand of(
            String customerCode,
            String ordNo,
            String ordDt
    ) {
        return new ScheduleFasstoWarehousingPollingCommand(customerCode, ordNo, ordDt);
    }
}
