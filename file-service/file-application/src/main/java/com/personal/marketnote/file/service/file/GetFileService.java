package com.personal.marketnote.file.service.file;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.domain.file.FileSort;
import com.personal.marketnote.common.domain.file.OwnerType;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.domain.file.ResizedFile;
import com.personal.marketnote.file.exception.FileNotFoundException;
import com.personal.marketnote.file.port.in.result.GetFilesResult;
import com.personal.marketnote.file.port.in.usecase.file.GetFileUseCase;
import com.personal.marketnote.file.port.out.file.FindFilePort;
import com.personal.marketnote.file.port.out.resized.FindResizedFilesPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetFileService implements GetFileUseCase {
    private final FindFilePort findFilePort;
    private final FindResizedFilesPort findResizedFilesPort;

    @Override
    public FileDomain getFile(Long id) {
        return findFilePort.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));
    }

    @Override
    public GetFilesResult getFiles(String ownerType, Long ownerId, String sort) {
        OwnerType type = OwnerType.from(ownerType);
        List<FileDomain> files = FormatValidator.hasValue(sort)
                ? findFilePort.findByOwnerAndSort(type, ownerId, FileSort.from(sort))
                : findFilePort.findByOwner(type, ownerId);

        List<Long> fileIds = files.stream().map(FileDomain::getId).toList();
        List<ResizedFile> resized = findResizedFilesPort.findByFileIds(fileIds);
        Map<Long, List<String>> fileIdToUrls = resized.stream()
                .collect(Collectors.groupingBy(ResizedFile::getFileId,
                        Collectors.mapping(ResizedFile::getS3Url, Collectors.toList())));

        List<GetFilesResult.FileItem> items = files.stream()
                .map(
                        file -> GetFilesResult.FileItem.from(file, fileIdToUrls)
                ).toList();
        return new GetFilesResult(items);
    }
}


