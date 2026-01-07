package com.personal.marketnote.file.port.in.usecase.file;

import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.port.in.result.GetFilesResult;

public interface GetFileUseCase {
    FileDomain getFile(Long id);

    GetFilesResult getFiles(String ownerType, Long ownerId, String sort);
}
