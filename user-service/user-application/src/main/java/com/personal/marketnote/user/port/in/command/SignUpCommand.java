package com.personal.marketnote.user.port.in.command;

import lombok.Getter;

@Getter
public class SignUpCommand {
    private final String nickname;
    private final String fullName;
    private final String phoneNumber;

    private SignUpCommand(String nickname, String fullName, String phoneNumber) {
        this.nickname = nickname;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public static SignUpCommand of(String nickname, String fullName, String phoneNumber) {
        return new SignUpCommand(nickname, fullName, phoneNumber);
    }
}
