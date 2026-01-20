package com.personal.marketnote.file.adapter.in.client.file.mapper;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.file.adapter.in.client.file.request.AddFilesRequest;
import com.personal.marketnote.file.port.in.command.AddFileCommand;
import com.personal.marketnote.file.port.in.command.AddFilesCommand;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FileRequestToCommandMapper {
    public static AddFilesCommand mapToCommand(AddFilesRequest addFilesRequest) {
        List<MultipartFile> files = addFilesRequest.getFile();
        List<String> sorts = addFilesRequest.getSort();
        List<String> extensions = addFilesRequest.getExtension();
        List<String> names = addFilesRequest.getName();

        if (FormatValidator.hasNoValue(files)) {
            return AddFilesCommand.builder()
                    .fileInfo(new ArrayList<>(0))
                    .ownerType(addFilesRequest.getOwnerType())
                    .ownerId(addFilesRequest.getOwnerId())
                    .build();
        }

        int fileSize = files.size();
        List<AddFileCommand> fileInfo = new ArrayList<>(fileSize);
        for (int i = 0; i < fileSize; i++) {
            fileInfo.add(AddFileCommand.builder()
                    .file(files.get(i))
                    .sort(getOrNull(sorts, i))
                    .extension(getOrNull(extensions, i))
                    .name(getOrNull(names, i))
                    .build());
        }

        return AddFilesCommand.builder()
                .fileInfo(fileInfo)
                .ownerType(addFilesRequest.getOwnerType())
                .ownerId(addFilesRequest.getOwnerId())
                .build();
    }

    private static String getOrNull(List<String> values, int index) {
        if (FormatValidator.hasNoValue(values) || index >= values.size()) {
            return null;
        }

        return values.get(index);
    }
}
