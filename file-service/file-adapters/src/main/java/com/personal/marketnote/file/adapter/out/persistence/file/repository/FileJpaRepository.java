package com.personal.marketnote.file.adapter.out.persistence.file.repository;

import com.personal.marketnote.common.domain.file.OwnerType;
import com.personal.marketnote.file.adapter.out.persistence.file.entity.FileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long> {
    List<FileJpaEntity> findAllByOwnerTypeAndOwnerIdOrderByIdAsc(OwnerType ownerType, Long ownerId);

    List<FileJpaEntity> findTop1ByOwnerTypeAndOwnerIdAndSortOrderByOrderNumDesc(OwnerType ownerType, Long ownerId, String sort);

    List<FileJpaEntity> findTop5ByOwnerTypeAndOwnerIdAndSortOrderByOrderNumDesc(OwnerType ownerType, Long ownerId, String sort);
}
