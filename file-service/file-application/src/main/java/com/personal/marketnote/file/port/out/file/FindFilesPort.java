package com.personal.marketnote.file.port.out.file;

import com.personal.marketnote.file.domain.file.FileDomain;
import com.personal.marketnote.file.domain.file.OwnerType;

import java.util.List;

public interface FindFilesPort {
    List<FileDomain> findByOwner(OwnerType ownerType, Long ownerId);

    List<FileDomain> findByOwnerAndSort(OwnerType ownerType, Long ownerId, String sort);
}


