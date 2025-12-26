package com.personal.shop.user.service.authentication.service;

import com.personal.shop.user.domain.user.User;
import com.personal.shop.user.exception.UserNotFoundException;
import com.personal.shop.user.port.in.result.GetUserResult;
import com.personal.shop.user.port.in.usecase.GetUserInfoUseCase;
import com.personal.shop.user.port.out.FindUserPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetUserInfoService implements GetUserInfoUseCase {
    private final FindUserPort findUserPort;

    @Override
    public GetUserResult getUser(Long id) {
        User user = findUserPort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return GetUserResult.of(id, user.getRole().getId());
    }
}
