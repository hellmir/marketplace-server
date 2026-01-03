package com.personal.marketnote.file.domain.file;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ResizedFile {
    private Long id;
    private Long fileId;
    private String size;
    private String s3Url;
    private LocalDateTime createdAt;
    private EntityStatus status;

    public static ResizedFile of(Long fileId, String size) {
        return ResizedFile.builder()
                .fileId(fileId)
                .size(size)
                .build();
    }

    public static ResizedFile of(Long fileId, String size, String s3Url) {
        return ResizedFile.builder()
                .fileId(fileId)
                .size(size)
                .s3Url(s3Url)
                .build();
    }

    public static ResizedFile of(Long id, Long fileId, String size, LocalDateTime createdAt, EntityStatus status) {
        return ResizedFile.builder()
                .id(id)
                .fileId(fileId)
                .size(size)
                .createdAt(createdAt)
                .status(status)
                .build();
    }

    public static ResizedFile of(Long id, Long fileId, String size, String s3Url, LocalDateTime createdAt, EntityStatus status) {
        return ResizedFile.builder()
                .id(id)
                .fileId(fileId)
                .size(size)
                .s3Url(s3Url)
                .createdAt(createdAt)
                .status(status)
                .build();
    }
}


