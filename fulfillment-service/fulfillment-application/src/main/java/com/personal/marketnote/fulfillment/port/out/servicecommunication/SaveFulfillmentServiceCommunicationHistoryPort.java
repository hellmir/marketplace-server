package com.personal.marketnote.fulfillment.port.out.servicecommunication;

import com.personal.marketnote.fulfillment.domain.servicecommunication.FulfillmentServiceCommunicationHistory;

public interface SaveFulfillmentServiceCommunicationHistoryPort {
    FulfillmentServiceCommunicationHistory save(FulfillmentServiceCommunicationHistory history);
}
