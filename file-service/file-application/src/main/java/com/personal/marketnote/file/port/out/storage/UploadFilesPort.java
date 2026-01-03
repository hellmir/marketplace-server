package com.personal.marketnote.file.port.out.storage;

import com.personal.marketnote.common.domain.file.OwnerType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFilesPort {
    List<String> uploadFiles(List<MultipartFile> files, OwnerType ownerType, Long ownerId);
}


