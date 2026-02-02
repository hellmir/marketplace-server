package com.personal.marketnote.reward.port.in.usecase.servicecommunication;

import com.personal.marketnote.reward.domain.servicecommunication.RewardServiceCommunicationHistory;
import com.personal.marketnote.reward.port.in.command.servicecommunication.RewardServiceCommunicationHistoryCommand;

public interface RecordRewardServiceCommunicationHistoryUseCase {
    RewardServiceCommunicationHistory record(RewardServiceCommunicationHistoryCommand command);
}
