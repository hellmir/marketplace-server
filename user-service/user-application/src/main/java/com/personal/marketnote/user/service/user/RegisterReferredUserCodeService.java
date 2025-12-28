package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.UserNotFoundException;
import com.personal.marketnote.user.port.in.usecase.user.RegisterReferredUserCodeUseCase;
import com.personal.marketnote.user.port.out.user.FindUserPort;
import com.personal.marketnote.user.port.out.user.UpdateUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.personal.marketnote.common.domain.exception.ExceptionCode.FIRST_ERROR_CODE;
import static com.personal.marketnote.user.exception.ExceptionMessage.USER_ID_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.personal.marketnote.user.exception.ExceptionMessage.USER_REFERENCE_CODE_NOT_FOUND_EXCEPTION_MESSAGE;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_UNCOMMITTED)
public class RegisterReferredUserCodeService implements RegisterReferredUserCodeUseCase {
    private final FindUserPort findUserPort;
    private final UpdateUserPort updateUserPort;

    @Override
    public void registerReferredUserCode(Long requestUserId, String referredUserCode) {
        if (!findUserPort.existsByReferenceCode(referredUserCode)) {
            throw new UserNotFoundException(
                    String.format(USER_REFERENCE_CODE_NOT_FOUND_EXCEPTION_MESSAGE, FIRST_ERROR_CODE, referredUserCode)
            );
        }

        User requestUser = findUserPort.findById(requestUserId)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                String.format(USER_ID_NOT_FOUND_EXCEPTION_MESSAGE, requestUserId)
                        )
                );

        requestUser.registerReferredUserCode(referredUserCode);
        updateUserPort.update(requestUser);
    }
}
