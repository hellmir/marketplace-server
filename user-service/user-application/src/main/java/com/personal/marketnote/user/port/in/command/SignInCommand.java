package com.personal.marketnote.user.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Getter
public class SignInCommand {
    private final String email;
    private final String password;

    public static SignInCommand of(String email, String password) {
        return new SignInCommand(email, password);
    }
}
