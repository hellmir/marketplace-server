package com.personal.shop.user.adapter.in.client.authentication.response;

import java.util.List;

public record AllowedEndpointForRoleResponse(String endpoint, List<RoleResponse> roles) {
}
