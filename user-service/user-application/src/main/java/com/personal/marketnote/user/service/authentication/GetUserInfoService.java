package com.personal.marketnote.user.service.authentication;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.UserNotFoundException;
import com.personal.marketnote.user.port.in.result.GetUserResult;
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
    public GetUserResult getUser(Long id) {
        User user = findUserPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_ID_NOT_FOUND_EXCEPTION_MESSAGE, id)));

        return GetUserResult.of(id, user.getRole().getId());
    }

    @Override
    public GetUserResult getUser(AuthVendor authVendor, String oidcId) {
        User user = findUserPort.findByAuthVendorAndOidcId(authVendor, oidcId)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_OIDC_ID_NOT_FOUND_EXCEPTION_MESSAGE, oidcId)));

        return GetUserResult.of(user.getId(), user.getRole().getId());
    }

    @Override
    public GetUserResult getUser(String phoneNumber) {
        User user = findUserPort.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_PHONE_NUMBER_NOT_FOUND_EXCEPTION_MESSAGE, phoneNumber))
                );

        return GetUserResult.of(user.getId(), user.getRole().getId());
    }
}
