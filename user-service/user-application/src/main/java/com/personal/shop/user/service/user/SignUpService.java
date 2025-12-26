package com.personal.shop.user.service.user;

import com.personal.shop.common.application.UseCase;
import com.personal.shop.user.domain.user.User;
import com.personal.shop.user.exception.UserExistsException;
import com.personal.shop.user.port.in.command.SignUpCommand;
import com.personal.shop.user.port.in.result.SignUpResult;
import com.personal.shop.user.port.in.usecase.SignUpUseCase;
import com.personal.shop.user.port.out.FindUserPort;
import com.personal.shop.user.port.out.SignUpPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
            throw new UserExistsException(oidcId);
        }

        return SignUpResult.from(
                signUpPort.saveUser(User.of(oidcId, signUpCommand.getNickname()))
        );
    }
}
