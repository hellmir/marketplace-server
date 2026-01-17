package com.personal.marketnote.community.port.out.file;

import com.personal.marketnote.common.application.file.port.in.result.GetFilesResult;
import com.personal.marketnote.common.domain.file.FileSort;

import java.util.Optional;

public interface FindReviewImagesPort {
    Optional<GetFilesResult> findImagesByReviewIdAndSort(Long reviewId, FileSort sort);
}
