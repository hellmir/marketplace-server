package com.personal.marketnote.reward.port.out.vendorcommunication;

import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorCommunicationHistory;

public interface RewardSaveVendorCommunicationHistoryPort {
    RewardVendorCommunicationHistory save(RewardVendorCommunicationHistory history);
}
