package com.personal.marketnote.file.mapper;

import com.personal.marketnote.common.domain.file.FileSort;
import com.personal.marketnote.common.domain.file.OwnerType;
import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.domain.file.FileDomainCreateState;
import com.personal.marketnote.file.port.in.command.AddFilesCommand;

import java.util.List;

public class FileCommandToDomainMapper {
    public static List<FileDomain> mapToDomain(AddFilesCommand addFilesCommand) {
        OwnerType ownerType = OwnerType.from(addFilesCommand.ownerType());

        return addFilesCommand.fileInfo()
                .stream()
                .map(fileInfo -> FileDomain.from(
                        FileDomainCreateState.builder()
                                .ownerType(ownerType)
                                .ownerId(addFilesCommand.ownerId())
                                .sort(FileSort.from(fileInfo.sort()))
                                .extension(fileInfo.extension())
                                .name(fileInfo.name())
                                .build()
                ))
                .toList();
    }
}
