package com.personal.marketnote.file.port.out.file;

import com.personal.marketnote.file.domain.file.FileDomain;

import java.util.List;

public interface SaveFilesPort {
    List<FileDomain> saveAll(List<FileDomain> fileDomains, List<String> s3Urls);
}


