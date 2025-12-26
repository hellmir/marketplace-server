package com.personal.marketnote.user.adapter.in.client.authentication.accesspermission;

import com.personal.marketnote.user.constant.AuthorizedHttpMethod;

import java.util.Set;

public record AuthorizedApi(
        String endpoint,
        AuthorizedHttpMethod httpMethod,
        Set<String> roleIds
) {
}
