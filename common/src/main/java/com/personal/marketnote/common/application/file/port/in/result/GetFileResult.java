package com.personal.marketnote.common.application.file.port.in.result;

import java.util.List;

public record GetFileResult(
        Long id,
        String sort,
        String extension,
        String name,
        String s3Url,
        List<String> resizedS3Urls,
        Long orderNum
) {
}
