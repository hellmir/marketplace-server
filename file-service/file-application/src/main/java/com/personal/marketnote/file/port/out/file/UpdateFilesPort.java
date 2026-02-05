package com.personal.marketnote.file.port.out.file;

import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.domain.file.ResizedFile;

import java.util.List;

public interface UpdateFilesPort {
    void update(List<FileDomain> files, List<ResizedFile> resizedFiles);
}


