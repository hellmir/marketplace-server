package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.domain.exception.illegalargument.novalue.LoginInfoNoValueException;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.InvalidPasswordException;
import com.personal.marketnote.user.port.in.command.SignInCommand;
import com.personal.marketnote.user.port.in.result.SignInResult;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.in.usecase.user.SignInUseCase;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_UNCOMMITTED)
public class SignInService implements SignInUseCase {
    private final GetUserUseCase getUserUseCase;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignInResult signIn(SignInCommand signInCommand, AuthVendor authVendor, String oidcId) {
        return SignInResult.from(getSignedUpUser(signInCommand, authVendor, oidcId));
    }

    private User getSignedUpUser(SignInCommand signInCommand, AuthVendor authVendor, String oidcId) {
        if (FormatValidator.hasValue(authVendor) && FormatValidator.hasValue(oidcId)) {
            return getUserUseCase.getUser(authVendor, oidcId);
        }

        String email = signInCommand.getEmail();
        if (FormatValidator.hasValue(email)) {
            User signedUpUser = getUserUseCase.getUser(email);
            validatePassword(signedUpUser, signInCommand.getPassword());

            return signedUpUser;
        }

        throw new LoginInfoNoValueException("회원 전화번호 또는 authVendor 및 oidcId 중 하나는 필수입니다.");
    }

    private void validatePassword(User user, String password) {
        if (!user.isValidPassword(passwordEncoder, password)) {
            throw new InvalidPasswordException();
        }
    }
}
