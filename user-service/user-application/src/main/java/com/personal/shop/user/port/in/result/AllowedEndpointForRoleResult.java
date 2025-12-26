package com.personal.shop.user.port.in.result;

import javax.management.relation.RoleResult;
import java.util.List;

public record AllowedEndpointForRoleResult(String endpoint, List<RoleResult> roles) {
}
