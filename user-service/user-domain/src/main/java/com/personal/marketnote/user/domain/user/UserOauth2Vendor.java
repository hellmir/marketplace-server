package com.personal.marketnote.user.domain.user;

import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserOauth2Vendor {
    private User user;
    private final AuthVendor authVendor;
    private String oidcId;

    private UserOauth2Vendor(AuthVendor authVendor, String oidcId) {
        this.authVendor = authVendor;
        this.oidcId = oidcId;
    }

    public static UserOauth2Vendor of(
            User user,
            AuthVendor authVendor,
            String oidcId
    ) {
        return new UserOauth2Vendor(user, authVendor, oidcId);
    }

    public static UserOauth2Vendor of(
            AuthVendor authVendor,
            String oidcId
    ) {
        return new UserOauth2Vendor(authVendor, oidcId);
    }

    public void addUser(User user) {
        if (!FormatValidator.hasValue(this.user)) {
            this.user = user;
        }
    }

    public void update(AuthVendor authVendor, String oidcId) {
        if (isMe(authVendor)) {
            this.oidcId = oidcId;
        }
    }

    private boolean isMe(AuthVendor authVendor) {
        return this.authVendor.isMe(authVendor);
    }
}
