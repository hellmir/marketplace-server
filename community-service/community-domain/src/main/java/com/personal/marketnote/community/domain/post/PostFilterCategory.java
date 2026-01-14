package com.personal.marketnote.community.domain.post;

import com.personal.marketnote.common.utility.FormatConverter;
import lombok.Getter;

@Getter
public enum PostFilterCategory {
    FAQ_CATEGORY("카테고리"),
    IS_PUBLIC("비밀글 여부"),
    IS_MINE("내 문의글 여부");

    private final String description;
    private final String camelCaseValue;

    PostFilterCategory(String description) {
        this.description = description;
        camelCaseValue = FormatConverter.snakeToCamel(name());
    }
}
