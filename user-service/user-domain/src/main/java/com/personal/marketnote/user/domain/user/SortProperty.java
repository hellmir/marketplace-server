package com.personal.marketnote.user.domain.user;

import lombok.Getter;

@Getter
public enum SortProperty {

    ID("회원 기본키"),
    NICKNAME("닉네임"),
    EMAIL("이메일 주소"),
    PHONE_NUMBER("전화번호");

    private final String lowerValue;
    private final String description;

    SortProperty(String description) {
        lowerValue = this.name().toLowerCase();
        this.description = description;
    }
}
