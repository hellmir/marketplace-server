package com.personal.marketnote.user.port.in.usecase;

import com.personal.marketnote.user.constant.AuthorizedHttpMethod;
import com.personal.marketnote.user.domain.authentication.Role;
import com.personal.marketnote.user.exception.RoleNotFoundException;
import com.personal.marketnote.user.port.in.result.AllowedEndpointForRoleResult;

import java.util.List;
import java.util.Map;

public interface GetRoleInfoUseCase {
    Map<AuthorizedHttpMethod, List<AllowedEndpointForRoleResult>> getAllAccessPermission();

    boolean existsRole(String roleId);

    Role getRoleById(String roleId) throws RoleNotFoundException;
}
