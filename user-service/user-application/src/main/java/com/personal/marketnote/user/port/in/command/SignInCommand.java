package com.personal.marketnote.user.port.in.command;

import lombok.Getter;

@Getter
public class SignInCommand {
    private final String email;
    private final String password;

    private SignInCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static SignInCommand of(String email, String password) {
        return new SignInCommand(email, password);
    }
}
