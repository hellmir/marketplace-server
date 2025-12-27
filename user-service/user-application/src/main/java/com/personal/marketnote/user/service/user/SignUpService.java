package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.domain.user.Terms;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.UserExistsException;
import com.personal.marketnote.user.port.in.command.SignUpCommand;
import com.personal.marketnote.user.port.in.result.SignUpResult;
import com.personal.marketnote.user.port.in.usecase.user.SignUpUseCase;
import com.personal.marketnote.user.port.out.user.FindTermsPort;
import com.personal.marketnote.user.port.out.user.FindUserPort;
import com.personal.marketnote.user.port.out.user.SaveUserPort;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.personal.marketnote.user.exception.ExceptionMessage.OIDC_ID_EXISTS_EXCEPTION_MESSAGE;
import static com.personal.marketnote.user.exception.ExceptionMessage.PHONE_NUMBER_EXISTS_EXCEPTION_MESSAGE;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_UNCOMMITTED)
public class SignUpService implements SignUpUseCase {
    private final SaveUserPort saveUserPort;
    private final FindUserPort findUserPort;
    private final FindTermsPort findTermsPort;

    @Override
    public SignUpResult signUp(SignUpCommand signUpCommand, AuthVendor authVendor, String oidcId) {
        if (!authVendor.isNative() && findUserPort.existsByOidcId(oidcId)) {
            throw new UserExistsException(String.format(OIDC_ID_EXISTS_EXCEPTION_MESSAGE, oidcId));
        }

        String phoneNumber = signUpCommand.getPhoneNumber();
        if (findUserPort.existsByPhoneNumber(phoneNumber)) {
            throw new UserExistsException(String.format(PHONE_NUMBER_EXISTS_EXCEPTION_MESSAGE, phoneNumber));
        }

        List<Terms> terms = findTermsPort.findAll();

        return SignUpResult.from(
                saveUserPort.save(
                        User.of(
                                authVendor,
                                oidcId,
                                signUpCommand.getNickname(),
                                signUpCommand.getFullName(),
                                signUpCommand.getPhoneNumber(),
                                terms
                        )
                )
        );
    }
}
