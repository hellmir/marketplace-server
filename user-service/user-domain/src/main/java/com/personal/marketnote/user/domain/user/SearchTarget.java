package com.personal.marketnote.user.domain.user;

import lombok.Getter;

@Getter
public enum SearchTarget {
    ID("회원 기본키"),
    NICKNAME("닉네임"),
    EMAIL("이메일 주소"),
    PHONE_NUMBER("전화번호"),
    REFERENCE_CODE("초대 코드");

    private final String lowerValue;
    private final String description;

    SearchTarget(String description) {
        lowerValue = this.name().toLowerCase();
        this.description = description;
    }
}
