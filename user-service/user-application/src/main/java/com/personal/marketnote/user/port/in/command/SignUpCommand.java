package com.personal.marketnote.user.port.in.command;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.Getter;

@Getter
public class SignUpCommand {
    private final String nickname;
    private final String email;
    private final String password;
    private final String fullName;
    private final String phoneNumber;

    private SignUpCommand(String nickname, String email, String password, String fullName, String phoneNumber) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public static SignUpCommand of(String nickname, String email, String password, String fullName, String phoneNumber) {
        return new SignUpCommand(nickname, email, password, fullName, phoneNumber);
    }

    public boolean hasPassword() {
        return FormatValidator.hasValue(password);
    }

    public boolean hasPhoneNumber() {
        return FormatValidator.hasValue(phoneNumber);
    }
}
