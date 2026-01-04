package com.personal.marketnote.file.adapter.out.persistence.file;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.common.domain.file.OwnerType;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.file.adapter.out.persistence.file.entity.FileJpaEntity;
import com.personal.marketnote.file.adapter.out.persistence.file.repository.FileJpaRepository;
import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.port.out.file.FindFilesPort;
import com.personal.marketnote.file.port.out.file.SaveFilesPort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class FilePersistenceAdapter implements SaveFilesPort, FindFilesPort {
    private final FileJpaRepository fileJpaRepository;

    @Override
    public List<FileDomain> saveAll(List<FileDomain> fileDomains, List<String> s3Urls) {
        if (!FormatValidator.hasValue(fileDomains)) {
            return List.of();
        }
        if (!FormatValidator.hasValue(s3Urls) || s3Urls.size() != fileDomains.size()) {
            throw new IllegalArgumentException("s3Urls size must match fileDomains size");
        }

        List<FileJpaEntity> toSave = new ArrayList<>(fileDomains.size());
        for (int i = 0; i < fileDomains.size(); i++) {
            toSave.add(FileJpaEntity.from(fileDomains.get(i), s3Urls.get(i)));
        }

        List<FileJpaEntity> savedList = fileJpaRepository.saveAll(toSave);
        savedList.forEach(FileJpaEntity::setIdToOrderNum);

        return savedList.stream()
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
                ))
                .toList();
    }

    @Override
    public List<FileDomain> findByOwner(OwnerType ownerType, Long ownerId) {
        return fileJpaRepository.findAllByOwnerTypeAndOwnerIdOrderByIdAsc(ownerType, ownerId).stream()
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
                ))
                .toList();
    }

    @Override
    public List<FileDomain> findByOwnerAndSort(OwnerType ownerType, Long ownerId, String sort) {
        return fileJpaRepository.findTop5ByOwnerTypeAndOwnerIdAndSortOrderByOrderNumDesc(ownerType, ownerId, sort).stream()
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
                ))
                .toList();
    }
}


