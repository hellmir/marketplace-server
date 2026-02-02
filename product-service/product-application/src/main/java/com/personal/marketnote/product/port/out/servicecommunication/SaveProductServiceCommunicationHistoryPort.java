package com.personal.marketnote.product.port.out.servicecommunication;

import com.personal.marketnote.product.domain.servicecommunication.ProductServiceCommunicationHistory;

public interface SaveProductServiceCommunicationHistoryPort {
    ProductServiceCommunicationHistory save(ProductServiceCommunicationHistory history);
}
