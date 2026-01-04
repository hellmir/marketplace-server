package com.personal.marketnote.product.port.out.file;

import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;

import java.util.Optional;

public interface FindProductCatalogImagePort {
    Optional<GetFilesResult> findCatalogImagesByProductId(Long productId);

    Optional<GetFilesResult> findImagesByProductIdAndSort(Long productId, String sort);
}


