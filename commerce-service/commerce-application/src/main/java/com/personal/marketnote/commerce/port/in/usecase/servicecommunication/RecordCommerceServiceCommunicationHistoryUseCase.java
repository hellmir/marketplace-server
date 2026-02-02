package com.personal.marketnote.commerce.port.in.usecase.servicecommunication;

import com.personal.marketnote.commerce.domain.servicecommunication.CommerceServiceCommunicationHistory;
import com.personal.marketnote.commerce.port.in.command.servicecommunication.CommerceServiceCommunicationHistoryCommand;

public interface RecordCommerceServiceCommunicationHistoryUseCase {
    CommerceServiceCommunicationHistory record(CommerceServiceCommunicationHistoryCommand command);
}
