package com.personal.marketnote.user.port.in.command;

import lombok.Getter;

@Getter
public class SignInCommand {
    private final String phoneNumber;

    private SignInCommand(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static SignInCommand of(String phoneNumber) {
        return new SignInCommand(phoneNumber);
    }
}
