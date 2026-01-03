package com.personal.marketnote.file.mapper;

import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.port.in.command.AddFilesCommand;

import java.util.List;

public class FileCommandToDomainMapper {
    public static List<FileDomain> mapToDomain(AddFilesCommand addFilesCommand) {
        return addFilesCommand.fileInfo()
                .stream()
                .map(fileInfo -> FileDomain.of(
                        addFilesCommand.ownerType(),
                        addFilesCommand.ownerId(),
                        fileInfo.sort(),
                        fileInfo.extension(),
                        fileInfo.name()
                ))
                .toList();
    }
}
