package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.in.command.UpdateUserInfoCommand;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.in.usecase.user.UpdateUserUseCase;
import com.personal.marketnote.user.port.out.user.UpdateUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_UNCOMMITTED, timeout = 180)
public class UpdateUserService implements UpdateUserUseCase {
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserPort updateUserPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUserInfo(Long userId, UpdateUserInfoCommand updateUserInfoCommand) {
        User user = getUserUseCase.getUser(userId);
        user.update(
                updateUserInfoCommand.getEmail(),
                updateUserInfoCommand.getNickname(),
                updateUserInfoCommand.getPhoneNumber(),
                updateUserInfoCommand.getPassword(),
                passwordEncoder
        );
        updateUserPort.update(user);
    }
}
