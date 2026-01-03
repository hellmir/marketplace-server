package com.personal.marketnote.file.port.in.usecase.file.result;

import com.personal.marketnote.file.domain.file.FileDomain;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.Map;

public record GetFilesResult(List<FileItem> files) {
    @Builder(access = AccessLevel.PRIVATE)
    public record FileItem(
            Long id,
            String sort,
            String extension,
            String name,
            String s3Url,
            List<String> resizedS3Urls,
            Long orderNum
    ) {
        public static GetFilesResult.FileItem from(FileDomain file, Map<Long, List<String>> fileIdToUrls) {
            return FileItem.builder()
                    .id(file.getId())
                    .sort(file.getSort().name())
                    .extension(file.getExtension())
                    .name(file.getName())
                    .s3Url(file.getS3Url())
                    .resizedS3Urls(fileIdToUrls.getOrDefault(file.getId(), List.of()))
                    .orderNum(file.getOrderNum())
                    .build();
        }
    }
}


