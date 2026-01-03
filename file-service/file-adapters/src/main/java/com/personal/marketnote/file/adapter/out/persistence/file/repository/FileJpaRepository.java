package com.personal.marketnote.file.adapter.out.persistence.file.repository;

import com.personal.marketnote.file.adapter.out.persistence.file.entity.FileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long> {
}


