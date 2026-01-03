package com.personal.marketnote.file.port.in.usecase.file;

import com.personal.marketnote.file.port.in.command.AddFilesCommand;

public interface FileUseCase {
    void addFiles(AddFilesCommand addFilesCommand);
}