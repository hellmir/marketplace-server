package com.personal.marketnote.user.port.in.usecase;

import com.personal.marketnote.user.port.in.command.SignUpCommand;
import com.personal.marketnote.user.port.in.result.SignUpResult;

public interface SignUpUseCase {
    SignUpResult signUp(SignUpCommand signUpCommand, String oidcId);
}
