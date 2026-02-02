package com.personal.marketnote.user.port.out.servicecommunication;

import com.personal.marketnote.user.domain.servicecommunication.UserServiceCommunicationHistory;

public interface SaveUserServiceCommunicationHistoryPort {
    UserServiceCommunicationHistory save(UserServiceCommunicationHistory history);
}
