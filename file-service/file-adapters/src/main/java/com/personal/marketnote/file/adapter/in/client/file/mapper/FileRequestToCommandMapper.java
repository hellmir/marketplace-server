package com.personal.marketnote.file.adapter.in.client.file.mapper;

import com.personal.marketnote.file.adapter.in.client.file.request.AddFileRequest;
import com.personal.marketnote.file.adapter.in.client.file.request.AddFilesRequest;
import com.personal.marketnote.file.port.in.command.AddFileCommand;
import com.personal.marketnote.file.port.in.command.AddFilesCommand;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileRequestToCommandMapper {
    public static AddFilesCommand mapToCommand(AddFilesRequest addFilesRequest) {
        return AddFilesCommand.builder()
                .fileInfo(addFilesRequest.getFileInfo().stream()
                        .map(FileRequestToCommandMapper::mapToCommand)
                        .toList()
                )
                .ownerType(addFilesRequest.getOwnerType())
                .ownerId(addFilesRequest.getOwnerId())
                .build();
    }

    private static AddFileCommand mapToCommand(AddFileRequest addFileRequest) {
        return AddFileCommand.builder()
                .file(addFileRequest.getFile())
                .sort(addFileRequest.getSort())
                .extension(addFileRequest.getExtension())
                .name(addFileRequest.getName())
                .build();
    }

    public static AddFilesCommand mapToCommand(
            String ownerType,
            Long ownerId,
            List<MultipartFile> files,
            List<String> sorts,
            List<String> extensions,
            List<String> names
    ) {
        return AddFilesCommand.builder()
                .fileInfo(files.stream()
                        .map(file -> AddFileCommand.builder()
                                .file(file)
                                .sort(sorts.get(files.indexOf(file)))
                                .extension(extensions.get(files.indexOf(file)))
                                .name(names.get(files.indexOf(file)))
                                .build())
                        .toList())
                .ownerType(ownerType)
                .ownerId(ownerId)
                .build();
    }

    private static String getOrNull(List<String> list, int index) {
        if (list == null || index >= list.size()) {
            return null;
        }
        return list.get(index);
    }
}
