package com.personal.marketnote.file.port.in.usecase.file;

import com.personal.marketnote.file.port.in.command.AddFilesCommand;

public interface AddFileUseCase {
    void addFiles(AddFilesCommand addFilesCommand);
}