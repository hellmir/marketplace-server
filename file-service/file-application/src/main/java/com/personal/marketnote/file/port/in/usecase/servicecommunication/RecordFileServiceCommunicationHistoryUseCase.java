package com.personal.marketnote.file.port.in.usecase.servicecommunication;

import com.personal.marketnote.file.domain.servicecommunication.FileServiceCommunicationHistory;
import com.personal.marketnote.file.port.in.command.servicecommunication.FileServiceCommunicationHistoryCommand;

public interface RecordFileServiceCommunicationHistoryUseCase {
    FileServiceCommunicationHistory record(FileServiceCommunicationHistoryCommand command);
}
