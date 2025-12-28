package com.personal.marketnote.user.port.in.result;

import com.personal.marketnote.user.domain.user.UserOauth2Vendor;

import java.util.List;
import java.util.stream.Collectors;

public record AccountInfoResult(
        List<AccountResult> accounts
) {
    public static AccountInfoResult from(List<UserOauth2Vendor> userOauth2Vendors) {
        return new AccountInfoResult(
                userOauth2Vendors.stream()
                        .map(AccountResult::from)
                        .collect(Collectors.toList())
        );
    }
}
