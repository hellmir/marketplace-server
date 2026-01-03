package com.personal.marketnote.file.domain.file;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum FileSort {
    PRODUCT_CATALOG_IMAGE("상품 카탈로그 이미지"),
    PRODUCT_REPRESENTATIVE_IMAGE("상품 대표 이미지"),
    PRODUCT_CONTENT_IMAGE("상품 본문 이미지"),
    ICON("아이콘"),
    ETC("기타");

    private final String description;

    public static FileSort from(String targetValue) throws IllegalArgumentException {
        return Arrays.stream(FileSort.values())
                .filter(fileSort -> FormatValidator.equals(fileSort.name(), targetValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid file sort: " + targetValue));
    }
}
