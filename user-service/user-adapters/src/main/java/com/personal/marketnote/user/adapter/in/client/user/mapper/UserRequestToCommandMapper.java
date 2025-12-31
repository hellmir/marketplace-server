package com.personal.marketnote.user.adapter.in.client.user.mapper;

import com.personal.marketnote.user.adapter.in.client.user.request.SignInRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.SignUpRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.UpdateUserInfoRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.verifyCodeRequest;
import com.personal.marketnote.user.port.in.command.SignInCommand;
import com.personal.marketnote.user.port.in.command.SignUpCommand;
import com.personal.marketnote.user.port.in.command.UpdateUserInfoCommand;
import com.personal.marketnote.user.port.in.command.VerifyCodeCommand;

public class UserRequestToCommandMapper {
    public static SignUpCommand mapToCommand(SignUpRequest signUpRequest) {
        return SignUpCommand.of(
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getVerificationCode(),
                signUpRequest.getNickname(),
                signUpRequest.getFullName(),
                signUpRequest.getPhoneNumber()
        );
    }

    public static SignInCommand mapToCommand(SignInRequest signInRequest) {
        return SignInCommand.of(signInRequest.getEmail(), signInRequest.getPassword());
    }

    public static UpdateUserInfoCommand mapToCommand(UpdateUserInfoRequest updateUserInfoRequest) {
        return UpdateUserInfoCommand.of(
                updateUserInfoRequest.getIsActive(),
                updateUserInfoRequest.getEmail(),
                updateUserInfoRequest.getNickname(),
                updateUserInfoRequest.getPhoneNumber(),
                updateUserInfoRequest.getPassword()
        );
    }

    public static VerifyCodeCommand mapToCommand(
            verifyCodeRequest verifyCodeRequest
    ) {
        return VerifyCodeCommand.of(
                verifyCodeRequest.getEmail(),
                verifyCodeRequest.getVerificationCode()
        );
    }
}
