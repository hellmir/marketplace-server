package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.domain.exception.illegalargument.novalue.UpdateTargetNoValueException;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.UserExistsException;
import com.personal.marketnote.user.port.in.command.UpdateUserInfoCommand;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.in.usecase.user.UpdateUserUseCase;
import com.personal.marketnote.user.port.out.user.FindUserPort;
import com.personal.marketnote.user.port.out.user.UpdateUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.*;
import static com.personal.marketnote.user.exception.ExceptionMessage.*;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_UNCOMMITTED, timeout = 180)
public class UpdateUserService implements UpdateUserUseCase {
    private final GetUserUseCase getUserUseCase;
    private final FindUserPort findUserPort;
    private final UpdateUserPort updateUserPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUserInfo(boolean isAdmin, Long id, UpdateUserInfoCommand updateUserInfoCommand) {
        User user = isAdmin
                ? getUserUseCase.getAllStatusUser(id)
                : getUserUseCase.getUser(id);
        updateTarget(isAdmin, updateUserInfoCommand, user);
        updateUserPort.update(user);
    }

    private void updateTarget(boolean isAdmin, UpdateUserInfoCommand updateUserInfoCommand, User user) {
        if (isAdmin && updateUserInfoCommand.hasIsActive()) {
            user.updateStatus(updateUserInfoCommand.isActive());
            return;
        }

        String password = updateUserInfoCommand.getPassword();
        if (updateUserInfoCommand.hasPassword()) {
            user.updatePassword(password, passwordEncoder);
            return;
        }

        String email = updateUserInfoCommand.getEmail();
        if (updateUserInfoCommand.hasEmail()) {
            user.validateDifferentEmail(email);
            validateDuplicateEmail(email);
            user.updateEmail(email);
            return;
        }

        String nickname = updateUserInfoCommand.getNickname();
        if (updateUserInfoCommand.hasNickname()) {
            user.validateDifferentNickname(nickname);
            validateDuplicateNickname(nickname);
            user.updateNickname(nickname);
            return;
        }

        String phoneNumber = updateUserInfoCommand.getPhoneNumber();
        if (updateUserInfoCommand.hasPhoneNumber()) {
            user.validateDifferentPhoneNumber(phoneNumber);
            validateDuplicatePhoneNumber(phoneNumber);
            user.updatePhoneNumber(phoneNumber);
            return;
        }

        throw new UpdateTargetNoValueException();
    }

    private void validateDuplicateEmail(String email) {
        if (findUserPort.existsByEmail(email)) {
            throw new UserExistsException(
                    String.format(EMAIL_ALREADY_EXISTS_EXCEPTION_MESSAGE, FOURTH_ERROR_CODE, email)
            );
        }
    }

    private void validateDuplicateNickname(String nickname) {
        if (findUserPort.existsByNickname(nickname)) {
            throw new UserExistsException(
                    String.format(NICKNAME_ALREADY_EXISTS_EXCEPTION_MESSAGE, FIFTH_ERROR_CODE, nickname)
            );
        }
    }

    private void validateDuplicatePhoneNumber(String phoneNumber) {
        if (findUserPort.existsByPhoneNumber(phoneNumber)) {
            throw new UserExistsException(
                    String.format(PHONE_NUMBER_ALREADY_EXISTS_EXCEPTION_MESSAGE, SIXTH_ERROR_CODE, phoneNumber)
            );
        }
    }
}
