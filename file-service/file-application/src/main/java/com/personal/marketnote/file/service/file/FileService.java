package com.personal.marketnote.file.service.file;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.domain.file.OwnerType;
import com.personal.marketnote.file.mapper.FileCommandToDomainMapper;
import com.personal.marketnote.file.port.in.command.AddFileCommand;
import com.personal.marketnote.file.port.in.command.AddFilesCommand;
import com.personal.marketnote.file.port.in.usecase.file.FileUseCase;
import com.personal.marketnote.file.port.out.file.SaveFilesPort;
import com.personal.marketnote.file.port.out.storage.UploadFilesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class FileService implements FileUseCase {
    private final UploadFilesPort uploadFilesPort;
    private final SaveFilesPort saveFilesPort;

    public void addFiles(AddFilesCommand addFilesCommand) {
        List<FileDomain> fileDomains = FileCommandToDomainMapper.mapToDomain(addFilesCommand);

        List<String> s3Urls = uploadFilesPort.uploadFiles(
                addFilesCommand.fileInfo()
                        .stream()
                        .map(AddFileCommand::file)
                        .toList(),
                OwnerType.from(addFilesCommand.ownerType()),
                addFilesCommand.ownerId()
        );

        saveFilesPort.saveAll(fileDomains, s3Urls);
    }
}
