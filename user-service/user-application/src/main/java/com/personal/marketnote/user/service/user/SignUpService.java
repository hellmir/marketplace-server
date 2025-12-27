package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.UserExistsException;
import com.personal.marketnote.user.port.in.command.SignUpCommand;
import com.personal.marketnote.user.port.in.result.SignUpResult;
import com.personal.marketnote.user.port.in.usecase.SignUpUseCase;
import com.personal.marketnote.user.port.out.FindUserPort;
import com.personal.marketnote.user.port.out.SignUpPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.personal.marketnote.user.exception.ExceptionMessage.OIDC_ID_EXISTS_EXCEPTION_MESSAGE;
import static com.personal.marketnote.user.exception.ExceptionMessage.PHONE_NUMBER_EXISTS_EXCEPTION_MESSAGE;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
public class SignUpService implements SignUpUseCase {
    private final SignUpPort signUpPort;
    private final FindUserPort findUserPort;

    @Override
    public SignUpResult signUp(SignUpCommand signUpCommand, String oidcId) {
        if (findUserPort.isUserExists(oidcId)) {
            throw new UserExistsException(String.format(OIDC_ID_EXISTS_EXCEPTION_MESSAGE, oidcId));
        }

        String phoneNumber = signUpCommand.getPhoneNumber();
        if (findUserPort.isUserExists(phoneNumber)) {
            throw new UserExistsException(String.format(PHONE_NUMBER_EXISTS_EXCEPTION_MESSAGE, phoneNumber));
        }

        return SignUpResult.from(
                signUpPort.saveUser(
                        User.of(
                                oidcId,
                                signUpCommand.getNickname(),
                                signUpCommand.getFullName(),
                                signUpCommand.getPhoneNumber()
                        )
                )
        );
    }
}
