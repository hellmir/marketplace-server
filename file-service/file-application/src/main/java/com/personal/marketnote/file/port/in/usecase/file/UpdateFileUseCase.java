package com.personal.marketnote.file.port.in.usecase.file;

import com.personal.marketnote.file.port.in.command.UpdateFilesCommand;

public interface UpdateFileUseCase {
    void updateFiles(UpdateFilesCommand updateFilesCommand);
}