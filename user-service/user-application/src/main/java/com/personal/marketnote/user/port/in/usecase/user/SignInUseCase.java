package com.personal.marketnote.user.port.in.usecase.user;

import com.personal.marketnote.user.port.in.command.SignInCommand;
import com.personal.marketnote.user.port.in.result.SignInResult;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

public interface SignInUseCase {
    SignInResult signIn(SignInCommand signInCommand, AuthVendor authVendor, String oidcId);
}
