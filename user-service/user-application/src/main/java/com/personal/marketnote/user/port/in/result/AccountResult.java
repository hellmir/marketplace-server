package com.personal.marketnote.user.port.in.result;

import com.personal.marketnote.user.domain.user.UserOauth2Vendor;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

public record AccountResult(
        AuthVendor oauth2VendorId,
        String oidcId
) {
    public static AccountResult from(UserOauth2Vendor userOauth2Vendor) {
        return new AccountResult(
                userOauth2Vendor.getAuthVendor(),
                userOauth2Vendor.getOidcId()
        );
    }
}
