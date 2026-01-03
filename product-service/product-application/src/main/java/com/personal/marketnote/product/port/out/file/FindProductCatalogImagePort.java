package com.personal.marketnote.product.port.out.file;

import com.personal.marketnote.product.port.out.file.dto.GetFilesResult;

import java.util.Optional;

public interface FindProductCatalogImagePort {
    Optional<GetFilesResult> findCatalogImagesByProductId(Long productId);
}


