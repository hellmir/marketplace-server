package com.personal.marketnote.file.port.out.resized;

import com.personal.marketnote.file.domain.file.ResizedFile;

import java.util.List;

public interface FindResizedFilesPort {
    List<ResizedFile> findByFileIds(List<Long> fileIds);
}


