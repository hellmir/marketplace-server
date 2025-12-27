package com.personal.marketnote.user.adapter.in.client.user.mapper;

import com.personal.marketnote.user.adapter.in.client.user.request.SignInRequest;
import com.personal.marketnote.user.adapter.in.client.user.request.SignUpRequest;
import com.personal.marketnote.user.port.in.command.SignInCommand;
import com.personal.marketnote.user.port.in.command.SignUpCommand;

public class UserRequestToCommandMapper {
    public static SignUpCommand mapToCommand(SignUpRequest signUpRequest) {
        return SignUpCommand.of(
                signUpRequest.getNickname(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getFullName(),
                signUpRequest.getPhoneNumber()
        );
    }

    public static SignInCommand mapToCommand(SignInRequest signInRequest) {
        return SignInCommand.of(signInRequest.getPhoneNumber());
    }
}
