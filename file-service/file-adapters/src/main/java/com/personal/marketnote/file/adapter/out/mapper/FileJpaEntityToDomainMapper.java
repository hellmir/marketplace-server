package com.personal.marketnote.file.adapter.out.mapper;

import com.personal.marketnote.file.adapter.out.persistence.file.entity.FileJpaEntity;
import com.personal.marketnote.file.domain.file.FileDomain;

import java.util.Optional;

public class FileJpaEntityToDomainMapper {
    public static Optional<FileDomain> mapToDomain(FileJpaEntity fileJpaEntity) {
        return Optional.ofNullable(fileJpaEntity)
                .map(entity -> FileDomain.of(
                        entity.getId(),
                        entity.getOwnerType(),
                        entity.getOwnerId(),
                        entity.getSort(),
                        entity.getExtension(),
                        entity.getName(),
                        entity.getS3Url(),
                        entity.getCreatedAt(),
                        entity.getStatus(),
                        entity.getOrderNum()
                ));
    }
}
