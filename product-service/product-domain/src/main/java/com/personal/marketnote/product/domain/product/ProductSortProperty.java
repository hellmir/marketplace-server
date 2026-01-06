package com.personal.marketnote.product.domain.product;

import com.personal.marketnote.common.utility.FormatConverter;
import lombok.Getter;

@Getter
public enum ProductSortProperty {
    ORDER_NUM("정렬 순서", "orderNum"),
    POPULARITY("인기도", "popularity"),
    ACCUMULATED_POINT("적립금", "createdAt"),
    DISCOUNT_PRICE("할인 가격", "modifiedAt");

    private final String description;
    private final String alternativeKey;
    private final String camelCaseValue;

    ProductSortProperty(String description, String alternativeKey) {
        this.description = description;
        this.alternativeKey = alternativeKey;
        camelCaseValue = FormatConverter.snakeToCamel(this.name());
    }
}
