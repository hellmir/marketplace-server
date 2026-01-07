package com.personal.marketnote.file.domain.file;

import com.personal.marketnote.common.adapter.out.persistence.audit.EntityStatus;
import com.personal.marketnote.common.domain.file.FileSort;
import com.personal.marketnote.common.domain.file.OwnerType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class FileDomain {
    private Long id;
    private OwnerType ownerType;
    private Long ownerId;
    private FileSort sort;
    private String extension;
    private String name;
    private String s3Url;
    private LocalDateTime createdAt;
    private EntityStatus status;
    private Long orderNum;

    public static FileDomain of(
            String ownerType,
            Long ownerId,
            String sort,
            String extension,
            String name
    ) {
        return FileDomain.builder()
                .ownerType(OwnerType.from(ownerType))
                .ownerId(ownerId)
                .sort(FileSort.from(sort))
                .extension(extension)
                .name(name)
                .build();
    }

    public static FileDomain of(
            Long id,
            OwnerType ownerType,
            Long ownerId,
            String sort,
            String extension,
            String name,
            String s3Url,
            LocalDateTime createdAt,
            EntityStatus status,
            Long orderNum
    ) {
        return FileDomain.builder()
                .id(id)
                .ownerType(ownerType)
                .ownerId(ownerId)
                .sort(FileSort.from(sort))
                .extension(extension)
                .name(name)
                .s3Url(s3Url)
                .createdAt(createdAt)
                .status(status)
                .orderNum(orderNum)
                .build();
    }

    public boolean isActive() {
        return status.isActive();
    }

    public boolean isInactive() {
        return status.isInactive();
    }

    public void delete() {
        status = EntityStatus.INACTIVE;
    }
}
