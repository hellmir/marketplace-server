package com.personal.marketnote.file.adapter.in.client.file.mapper;

import com.personal.marketnote.file.adapter.in.client.file.request.AddFileRequest;
import com.personal.marketnote.file.adapter.in.client.file.request.AddFilesRequest;
import com.personal.marketnote.file.port.in.command.AddFileCommand;
import com.personal.marketnote.file.port.in.command.AddFilesCommand;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileRequestToCommandMapper {
    public static AddFilesCommand mapToCommand(AddFilesRequest addFilesRequest) {
        List<MultipartFile> files = addFilesRequest.getFile();
        List<String> sorts = addFilesRequest.getSort();
        List<String> extensions = addFilesRequest.getExtension();
        List<String> names = addFilesRequest.getName();

        java.util.List<AddFileCommand> fileInfo = new java.util.ArrayList<>(files == null ? 0 : files.size());
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                fileInfo.add(AddFileCommand.builder()
                        .file(files.get(i))
                        .sort(getOrNull(sorts, i))
                        .extension(getOrNull(extensions, i))
                        .name(getOrNull(names, i))
                        .build());
            }
        }

        return AddFilesCommand.builder()
                .fileInfo(fileInfo)
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
