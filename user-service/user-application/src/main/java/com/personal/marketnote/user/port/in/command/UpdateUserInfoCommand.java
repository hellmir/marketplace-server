package com.personal.marketnote.user.port.in.command;

import lombok.Getter;

@Getter
public class UpdateUserInfoCommand {
    private final String email;
    private final String nickname;
    private final String phoneNumber;
    private final String password;

    private UpdateUserInfoCommand(String email, String nickname, String phoneNumber, String password) {
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public static UpdateUserInfoCommand of(String email, String nickname, String phoneNumber, String password) {
        return new UpdateUserInfoCommand(email, nickname, phoneNumber, password);
    }
}
