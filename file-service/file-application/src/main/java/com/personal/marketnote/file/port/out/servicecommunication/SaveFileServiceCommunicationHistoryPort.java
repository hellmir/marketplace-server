package com.personal.marketnote.file.port.out.servicecommunication;

import com.personal.marketnote.file.domain.servicecommunication.FileServiceCommunicationHistory;

public interface SaveFileServiceCommunicationHistoryPort {
    FileServiceCommunicationHistory save(FileServiceCommunicationHistory history);
}
