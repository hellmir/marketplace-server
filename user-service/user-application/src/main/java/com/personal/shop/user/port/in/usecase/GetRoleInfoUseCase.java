package com.personal.shop.user.port.in.usecase;

import com.personal.shop.user.constant.AuthorizedHttpMethod;
import com.personal.shop.user.domain.authentication.Role;
import com.personal.shop.user.exception.RoleNotFoundException;
import com.personal.shop.user.port.in.result.AllowedEndpointForRoleResult;

import java.util.List;
import java.util.Map;

public interface GetRoleInfoUseCase {
    Map<AuthorizedHttpMethod, List<AllowedEndpointForRoleResult>> getAllAccessPermission();

    boolean existsRole(String roleId);

    Role getRoleById(String roleId) throws RoleNotFoundException;
}
