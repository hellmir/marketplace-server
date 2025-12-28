package com.personal.marketnote.user.port.in.command;

import com.personal.marketnote.common.utility.FormatValidator;
import lombok.Getter;

@Getter
public class SignUpCommand {
    private final String email;
    private final String password;
    private final String verificationCode;
    private final String nickname;
    private final String fullName;
    private final String phoneNumber;

    private SignUpCommand(
            String email, String password, String verificationCode, String nickname, String fullName, String phoneNumber
    ) {
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.nickname = nickname;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public static SignUpCommand of(
            String email, String password, String verificationCode, String nickname, String fullName, String phoneNumber
    ) {
        return new SignUpCommand(email, password, verificationCode, nickname, fullName, phoneNumber);
    }

    public boolean hasPassword() {
        return FormatValidator.hasValue(password);
    }

    public boolean hasPhoneNumber() {
        return FormatValidator.hasValue(phoneNumber);
    }
}
