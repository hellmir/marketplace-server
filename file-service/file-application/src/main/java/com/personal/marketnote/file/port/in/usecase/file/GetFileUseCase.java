package com.personal.marketnote.file.port.in.usecase.file;

import com.personal.marketnote.common.domain.file.OwnerType;
import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.domain.file.ResizedFile;
import com.personal.marketnote.file.port.in.result.GetFilesResult;

import java.util.List;

public interface GetFileUseCase {
    FileDomain getFile(Long id);

    GetFilesResult getFiles(String ownerType, Long ownerId, String sort);

    List<FileDomain> getFiles(OwnerType ownerType, Long ownerId, String sort);

    List<ResizedFile> getResizedFiles(List<FileDomain> files);
}
