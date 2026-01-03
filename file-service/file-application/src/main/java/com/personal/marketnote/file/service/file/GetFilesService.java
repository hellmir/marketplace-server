package com.personal.marketnote.file.service.file;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.domain.file.OwnerType;
import com.personal.marketnote.file.domain.file.ResizedFile;
import com.personal.marketnote.file.port.in.usecase.file.GetFilesUseCase;
import com.personal.marketnote.file.port.in.usecase.file.result.GetFilesResult;
import com.personal.marketnote.file.port.out.file.FindFilesPort;
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
public class GetFilesService implements GetFilesUseCase {
    private final FindFilesPort findFilesPort;
    private final FindResizedFilesPort findResizedFilesPort;

    @Override
    public GetFilesResult getFiles(String ownerType, Long ownerId, String sort) {
        OwnerType type = OwnerType.from(ownerType);
        List<FileDomain> files = (sort == null || sort.isBlank())
                ? findFilesPort.findByOwner(type, ownerId)
                : findFilesPort.findByOwnerAndSort(type, ownerId, sort);

        List<Long> fileIds = files.stream().map(FileDomain::getId).toList();
        List<ResizedFile> resized = findResizedFilesPort.findByFileIds(fileIds);
        Map<Long, List<String>> fileIdToSizes = resized.stream()
                .collect(Collectors.groupingBy(ResizedFile::getFileId,
                        Collectors.mapping(ResizedFile::getSize, Collectors.toList())));

        List<GetFilesResult.FileItem> items = files.stream().map(f ->
                new GetFilesResult.FileItem(
                        f.getId(),
                        f.getSort().name(),
                        f.getExtension(),
                        f.getName(),
                        f.getS3Url(),
                        fileIdToSizes.getOrDefault(f.getId(), List.of())
                )
        ).toList();
        return new GetFilesResult(items);
    }
}


