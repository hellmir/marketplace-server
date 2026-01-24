package com.personal.marketnote.fulfillment.port.out.vendorcommunication;

import com.personal.marketnote.fulfillment.domain.vendorcommunication.FulfillmentVendorCommunicationHistory;

public interface SaveFulfillmentVendorCommunicationHistoryPort {
    FulfillmentVendorCommunicationHistory save(FulfillmentVendorCommunicationHistory history);
}
