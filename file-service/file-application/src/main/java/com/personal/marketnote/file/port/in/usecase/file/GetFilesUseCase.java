package com.personal.marketnote.file.port.in.usecase.file;

import com.personal.marketnote.file.port.in.result.GetFilesResult;

public interface GetFilesUseCase {
    GetFilesResult getFiles(String ownerType, Long ownerId, String sort);
}


