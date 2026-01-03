package com.personal.marketnote.product.port.out.file.dto;

import java.util.List;

public record GetFilesResult(List<FileItem> files) {
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


