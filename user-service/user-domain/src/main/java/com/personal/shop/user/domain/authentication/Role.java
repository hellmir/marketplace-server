package com.personal.shop.user.domain.authentication;

import lombok.Getter;

@Getter
public class Role {
    private static final String ROLE_PREFIX = "ROLE_";

    private String id;
    private String name;
    private String code;

    private Role(String id, String name) {
        this.id = id;
        this.name = name;
        code = id.substring(ROLE_PREFIX.length());
    }

    public static Role of(String id, String name) {
        return new Role(id, name);
    }

    public static Role getBuyer() {
        return new Role("ROLE_BUYER", "구매자");
    }

    public static Role getGuest() {
        return new Role("ROLE_GUEST", "게스트");
    }

    public String getCode() {
        if (this.code == null) {
            this.code = id.substring(ROLE_PREFIX.length());
        }
        return this.code;
    }

    public boolean isGuest() {
        return id.equals("ROLE_GUEST");
    }

    @Override
    public String toString() {
        return "[ Role=\"" + id + "\" ]";
    }
}
