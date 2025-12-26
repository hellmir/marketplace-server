package com.personal.shop.user.adapter.in.client.authentication.accesspermission;

import com.personal.shop.user.constant.AuthorizedHttpMethod;

import java.util.Set;

public record AuthorizedApi(
        String endpoint,
        AuthorizedHttpMethod httpMethod,
        Set<String> roleIds
) {
}
