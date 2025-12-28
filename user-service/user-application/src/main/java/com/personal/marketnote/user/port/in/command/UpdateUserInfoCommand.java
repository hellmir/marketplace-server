package com.personal.marketnote.user.port.in.command;

import com.personal.marketnote.common.utility.FormatValidator;
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

    public boolean hasPassword() {
        return FormatValidator.hasValue(password);
    }

    public boolean hasEmail() {
        return FormatValidator.hasValue(email);
    }

    public boolean hasNickname() {
        return FormatValidator.hasValue(nickname);
    }

    public boolean hasPhoneNumber() {
        return FormatValidator.hasValue(phoneNumber);
    }
}
