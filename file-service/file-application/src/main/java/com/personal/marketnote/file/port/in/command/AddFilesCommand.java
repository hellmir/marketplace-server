package com.personal.marketnote.file.port.in.command;

import lombok.Builder;

import java.util.List;

@Builder
public record AddFilesCommand(
        List<AddFileCommand> fileInfo,
        String ownerType,
        Long ownerId
) {
}
