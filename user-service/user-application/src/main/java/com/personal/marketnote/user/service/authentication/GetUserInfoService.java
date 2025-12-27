package com.personal.marketnote.user.service.authentication;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.UserNotFoundException;
import com.personal.marketnote.user.port.in.usecase.authentication.GetUserInfoUseCase;
import com.personal.marketnote.user.port.out.user.FindUserPort;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.personal.marketnote.user.exception.ExceptionMessage.*;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED, readOnly = true, timeout = 180)
public class GetUserInfoService implements GetUserInfoUseCase {
    private final FindUserPort findUserPort;

    @Override
    public User getUser(Long id) {
        return findUserPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_ID_NOT_FOUND_EXCEPTION_MESSAGE, id)));
    }

    @Override
    public User getUser(AuthVendor authVendor, String oidcId) {
        return findUserPort.findByAuthVendorAndOidcId(authVendor, oidcId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_OIDC_ID_NOT_FOUND_EXCEPTION_MESSAGE, oidcId)));
    }

    @Override
    public User getUser(String email) {
        return findUserPort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_EMAIL_NOT_FOUND_EXCEPTION_MESSAGE, email))
                );
    }
}
