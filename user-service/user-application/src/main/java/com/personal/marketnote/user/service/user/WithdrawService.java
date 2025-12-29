package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.port.in.usecase.user.GetUserUseCase;
import com.personal.marketnote.user.port.in.usecase.user.WithdrawUseCase;
import com.personal.marketnote.user.port.out.user.UpdateUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_UNCOMMITTED, timeout = 180)
public class WithdrawService implements WithdrawUseCase {
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserPort updateUserPort;

    @Override
    public void withdrawUser(Long id) {
        User user = getUserUseCase.getAllStatusUser(id);
        user.withdraw();
        updateUserPort.update(user);
    }
}
