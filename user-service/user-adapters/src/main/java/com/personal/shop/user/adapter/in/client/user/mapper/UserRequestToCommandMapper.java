package com.personal.shop.user.adapter.in.client.user.mapper;

import com.personal.shop.user.adapter.in.client.user.request.SignUpRequest;
import com.personal.shop.user.port.in.command.SignUpCommand;

public class UserRequestToCommandMapper {
    public static SignUpCommand mapToCommand(SignUpRequest signUpRequest) {
        return SignUpCommand.of(signUpRequest.getNickname());
    }
}
