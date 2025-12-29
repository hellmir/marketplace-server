package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.UserNotFoundException;
import com.personal.marketnote.user.port.in.result.GetUserResult;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.out.user.FindUserPort;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.personal.marketnote.user.exception.ExceptionMessage.*;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED, readOnly = true)
public class GetUserService implements GetUserUseCase {
    private final FindUserPort findUserPort;

    @Override
    public GetUserResult getUserInfo(Long id) {
        return GetUserResult.from(getUser(id));
    }

    @Override
    public GetUserResult getAllStatusUserInfo(Long id) {
        return GetUserResult.from(getAllStatusUser(id));
    }

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
    public User getAllStatusUser(Long id) {
        return findUserPort.findAllStatusUserById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_ID_NOT_FOUND_EXCEPTION_MESSAGE, id)));
    }

    @Override
    public User getAllStatusUser(String email) throws UserNotFoundException {
        return findUserPort.findAllStatusUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_EMAIL_NOT_FOUND_EXCEPTION_MESSAGE, email))
                );
    }
}
