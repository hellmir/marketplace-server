package com.personal.marketnote.community.domain.review;

import com.personal.marketnote.common.utility.FormatConverter;
import lombok.Getter;

@Getter
public enum ReviewSortProperty {
    ID("기본키(최신순)"),
    ORDER_NUM("정렬 순서");

    private final String description;
    private final String camelCaseValue;

    ReviewSortProperty(String description) {
        this.description = description;
        camelCaseValue = FormatConverter.snakeToCamel(this.name());
    }
}
