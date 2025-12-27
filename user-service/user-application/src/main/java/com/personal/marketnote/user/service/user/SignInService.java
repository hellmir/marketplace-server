package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.domain.exception.illegalargument.novalue.LoginInfoNoValueException;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.port.in.command.SignInCommand;
import com.personal.marketnote.user.port.in.result.GetUserResult;
import com.personal.marketnote.user.port.in.result.SignInResult;
import com.personal.marketnote.user.port.in.usecase.authentication.GetUserInfoUseCase;
import com.personal.marketnote.user.port.in.usecase.user.SignInUseCase;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_UNCOMMITTED)
public class SignInService implements SignInUseCase {
    private final GetUserInfoUseCase getUserInfoUseCase;

    @Override
    public SignInResult signIn(SignInCommand signInCommand, AuthVendor authVendor, String oidcId) {
        return SignInResult.from(getSignedUpUser(signInCommand.getPhoneNumber(), authVendor, oidcId));
    }

    private GetUserResult getSignedUpUser(String phoneNumber, AuthVendor authVendor, String oidcId) {
        if (FormatValidator.hasValue(authVendor) && FormatValidator.hasValue(oidcId)) {
            return getUserInfoUseCase.getUser(authVendor, oidcId);
        }

        if (FormatValidator.hasValue(phoneNumber)) {
            return getUserInfoUseCase.getUser(phoneNumber);
        }

        throw new LoginInfoNoValueException("회원 전화번호 또는 authVendor 및 oidcId 중 하나는 필수입니다.");
    }
}
