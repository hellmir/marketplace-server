package com.personal.marketnote.fulfillment.port.out.scheduler;

public interface ScheduleFasstoWarehousingPollingPort {
    void schedule(ScheduleFasstoWarehousingPollingCommand command);
}
