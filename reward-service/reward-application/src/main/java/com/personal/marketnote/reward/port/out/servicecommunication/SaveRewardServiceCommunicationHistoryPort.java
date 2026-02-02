package com.personal.marketnote.reward.port.out.servicecommunication;

import com.personal.marketnote.reward.domain.servicecommunication.RewardServiceCommunicationHistory;

public interface SaveRewardServiceCommunicationHistoryPort {
    RewardServiceCommunicationHistory save(RewardServiceCommunicationHistory history);
}
