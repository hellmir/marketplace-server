package com.personal.marketnote.file.port.out.file;

import com.personal.marketnote.common.domain.file.FileSort;
import com.personal.marketnote.common.domain.file.OwnerType;
import com.personal.marketnote.file.domain.file.FileDomain;

import java.util.List;
import java.util.Optional;

public interface FindFilePort {
    Optional<FileDomain> findById(Long id);

    List<FileDomain> findByOwner(OwnerType ownerType, Long ownerId);

    List<FileDomain> findByOwnerAndSort(OwnerType ownerType, Long ownerId, FileSort sort);
}


