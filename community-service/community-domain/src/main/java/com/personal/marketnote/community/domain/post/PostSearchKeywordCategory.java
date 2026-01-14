package com.personal.marketnote.community.domain.post;

import com.personal.marketnote.common.utility.FormatConverter;
import lombok.Getter;

@Getter
public enum PostSearchKeywordCategory {
    TITLE("게시글 제목"),
    CONTENT("게시글 내용"),
    PRODUCT_NAME("상품명"),
    BRAND_NAME("브랜드명");

    private final String description;
    private final String camelCaseValue;

    PostSearchKeywordCategory(String description) {
        this.description = description;
        this.camelCaseValue = FormatConverter.snakeToCamel(this.name());
    }
}
