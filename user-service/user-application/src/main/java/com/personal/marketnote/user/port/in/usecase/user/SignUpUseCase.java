package com.personal.marketnote.user.port.in.usecase.user;

import com.personal.marketnote.user.port.in.command.SignUpCommand;
import com.personal.marketnote.user.port.in.result.SignUpResult;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

public interface SignUpUseCase {
    SignUpResult signUp(SignUpCommand signUpCommand, AuthVendor authVendor, String oidcId);
}
