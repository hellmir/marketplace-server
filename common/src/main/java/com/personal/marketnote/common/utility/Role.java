package com.personal.marketnote.common.utility;

public enum Role {
    ROLE_ADMIN("관리자"),
    ROLE_SELLER("판매자"),
    ROLE_BUYER("구매자"),
    ROLE_GUEST("게스트"),
    ROLE_ANONYMOUS("비로그인 사용자");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static boolean isAdmin(String role) {
        return role.equals(ROLE_ADMIN.name());
    }

    public static boolean isSeller(String role) {
        return role.equals(ROLE_SELLER.name());
    }
}
