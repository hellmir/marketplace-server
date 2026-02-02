package com.personal.marketnote.commerce.port.out.servicecommunication;

import com.personal.marketnote.commerce.domain.servicecommunication.CommerceServiceCommunicationHistory;

public interface SaveCommerceServiceCommunicationHistoryPort {
    CommerceServiceCommunicationHistory save(CommerceServiceCommunicationHistory history);
}
