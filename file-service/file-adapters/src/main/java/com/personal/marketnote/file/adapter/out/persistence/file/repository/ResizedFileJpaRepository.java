package com.personal.marketnote.file.adapter.out.persistence.file.repository;

import com.personal.marketnote.file.adapter.out.persistence.file.entity.ResizedFileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResizedFileJpaRepository extends JpaRepository<ResizedFileJpaEntity, Long> {
    List<ResizedFileJpaEntity> findAllByFile_IdIn(List<Long> fileIds);
}


