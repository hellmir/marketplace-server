package com.personal.marketnote.reward.port.in.usecase.vendorcommunication;

import com.personal.marketnote.reward.domain.vendorcommunication.RewardVendorCommunicationHistory;
import com.personal.marketnote.reward.port.in.command.vendorcommunication.RewardVendorCommunicationHistoryCommand;

public interface RewardRecordVendorCommunicationHistoryUseCase {
    RewardVendorCommunicationHistory record(RewardVendorCommunicationHistoryCommand command);
}
