package com.personal.marketnote.file.adapter.out.persistence.file.repository;

import com.personal.marketnote.file.adapter.out.persistence.file.entity.FileJpaEntity;
import com.personal.marketnote.file.domain.file.OwnerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long> {
    List<FileJpaEntity> findAllByOwnerTypeAndOwnerIdOrderByIdAsc(OwnerType ownerType, Long ownerId);

    List<FileJpaEntity> findAllByOwnerTypeAndOwnerIdAndSortOrderByIdAsc(OwnerType ownerType, Long ownerId, String sort);
}


