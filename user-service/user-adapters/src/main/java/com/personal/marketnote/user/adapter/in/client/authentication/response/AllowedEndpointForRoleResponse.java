package com.personal.marketnote.user.adapter.in.client.authentication.response;

import java.util.List;

public record AllowedEndpointForRoleResponse(String endpoint, List<RoleResponse> roles) {
}
