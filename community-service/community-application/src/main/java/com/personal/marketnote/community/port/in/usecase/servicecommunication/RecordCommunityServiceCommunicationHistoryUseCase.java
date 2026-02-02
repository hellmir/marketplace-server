package com.personal.marketnote.community.port.in.usecase.servicecommunication;

import com.personal.marketnote.community.domain.servicecommunication.CommunityServiceCommunicationHistory;
import com.personal.marketnote.community.port.in.command.servicecommunication.CommunityServiceCommunicationHistoryCommand;

public interface RecordCommunityServiceCommunicationHistoryUseCase {
    CommunityServiceCommunicationHistory record(CommunityServiceCommunicationHistoryCommand command);
}
