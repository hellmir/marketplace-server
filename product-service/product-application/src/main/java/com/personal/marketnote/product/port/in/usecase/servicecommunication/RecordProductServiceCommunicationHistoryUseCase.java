package com.personal.marketnote.product.port.in.usecase.servicecommunication;

import com.personal.marketnote.product.domain.servicecommunication.ProductServiceCommunicationHistory;
import com.personal.marketnote.product.port.in.command.servicecommunication.ProductServiceCommunicationHistoryCommand;

public interface RecordProductServiceCommunicationHistoryUseCase {
    ProductServiceCommunicationHistory record(ProductServiceCommunicationHistoryCommand command);
}
