package com.personal.marketnote.community.port.out.servicecommunication;

import com.personal.marketnote.community.domain.servicecommunication.CommunityServiceCommunicationHistory;

public interface SaveCommunityServiceCommunicationHistoryPort {
    CommunityServiceCommunicationHistory save(CommunityServiceCommunicationHistory history);
}
