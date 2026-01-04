package com.personal.marketnote.product.port.out.file;

import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.common.domain.file.FileSort;

import java.util.Optional;

public interface FindProductImagesPort {
    Optional<GetFilesResult> findImagesByProductIdAndSort(Long productId, FileSort sort);
}


