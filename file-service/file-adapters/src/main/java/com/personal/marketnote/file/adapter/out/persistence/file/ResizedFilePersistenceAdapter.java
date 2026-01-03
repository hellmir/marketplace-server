package com.personal.marketnote.file.adapter.out.persistence.file;

import com.personal.marketnote.common.adapter.out.PersistenceAdapter;
import com.personal.marketnote.file.adapter.out.persistence.file.entity.FileJpaEntity;
import com.personal.marketnote.file.adapter.out.persistence.file.entity.ResizedFileJpaEntity;
import com.personal.marketnote.file.adapter.out.persistence.file.repository.FileJpaRepository;
import com.personal.marketnote.file.adapter.out.persistence.file.repository.ResizedFileJpaRepository;
import com.personal.marketnote.file.domain.file.ResizedFile;
import com.personal.marketnote.file.port.out.resized.SaveResizedFilesPort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class ResizedFilePersistenceAdapter implements SaveResizedFilesPort {
    private final FileJpaRepository fileJpaRepository;
    private final ResizedFileJpaRepository resizedFileJpaRepository;

    @Override
    public void saveAll(List<ResizedFile> resizedFiles) {
        if (resizedFiles == null || resizedFiles.isEmpty()) {
            return;
        }
        List<ResizedFileJpaEntity> toSave = new ArrayList<>(resizedFiles.size());
        for (ResizedFile f : resizedFiles) {
            FileJpaEntity fileRef = fileJpaRepository.getReferenceById(f.getFileId());
            toSave.add(ResizedFileJpaEntity.of(fileRef, f.getSize()));
        }
        resizedFileJpaRepository.saveAll(toSave);
    }
}


