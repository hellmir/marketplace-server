package com.personal.marketnote.user.port.in.usecase.servicecommunication;

import com.personal.marketnote.user.domain.servicecommunication.UserServiceCommunicationHistory;
import com.personal.marketnote.user.port.in.command.servicecommunication.UserServiceCommunicationHistoryCommand;

public interface RecordUserServiceCommunicationHistoryUseCase {
    UserServiceCommunicationHistory record(UserServiceCommunicationHistoryCommand command);
}
