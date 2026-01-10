package com.personal.marketnote.common.domain.file;

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
    CANCEL_REASON_IMAGE("취소 사유 이미지"),
    EXCHANGE_REASON_IMAGE("교환 사유 이미지"),
    REFUND_REASON_IMAGE("환불 사유 이미지"),
    REVIEW_IMAGE("리뷰 이미지"),
    ICON("아이콘"),
    ETC("기타");

    private final String description;

    public static FileSort from(String targetValue) throws IllegalArgumentException {
        return Arrays.stream(FileSort.values())
                .filter(fileSort -> FormatValidator.equals(fileSort.name(), targetValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid file sort: " + targetValue));
    }

    public boolean isCatalogImage() {
        return this == FileSort.PRODUCT_CATALOG_IMAGE;
    }

    public boolean isRepresentativeImage() {
        return this == FileSort.PRODUCT_REPRESENTATIVE_IMAGE;
    }
}
