package com.personal.shop.user.port.in.usecase;

import com.personal.shop.user.port.in.command.SignUpCommand;
import com.personal.shop.user.port.in.result.SignUpResult;

public interface SignUpUseCase {
    SignUpResult signUp(SignUpCommand signUpCommand, String oidcId);
}
