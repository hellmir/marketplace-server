package com.personal.marketnote.user.port.in.command;

import lombok.Getter;

@Getter
public class SignUpCommand {
    private final String nickname;

    private SignUpCommand(String nickname) {
        this.nickname = nickname;
    }

    public static SignUpCommand of(String nickname) {
        return new SignUpCommand(nickname);
    }
}
