package com.personal.marketnote.file.port.in.usecase.file.result;

import java.util.List;

public record GetFilesResult(List<FileItem> files) {
    public record FileItem(
            Long id,
            String sort,
            String extension,
            String name,
            String s3Url,
            List<String> resizedSizes
    ) {
    }
}


