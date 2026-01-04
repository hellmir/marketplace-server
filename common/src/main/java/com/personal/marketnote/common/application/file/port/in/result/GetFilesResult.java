package com.personal.marketnote.common.application.file.port.in.result;

import java.util.List;

public record GetFilesResult(List<FileItem> images) {
    public record FileItem(
            Long id,
            String sort,
            String extension,
            String name,
            String s3Url,
            List<String> resizedS3Urls,
            Long orderNum
    ) {
    }
}
