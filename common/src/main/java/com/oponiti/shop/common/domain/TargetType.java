package com.oponiti.shop.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TargetType {
    RECIPE_IMAGE("레시피 이미지");

    private final String description;
}
