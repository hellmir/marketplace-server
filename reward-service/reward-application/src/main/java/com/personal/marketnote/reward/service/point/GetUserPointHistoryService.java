package com.personal.marketnote.reward.service.point;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.common.utility.FormatValidator;
import com.personal.marketnote.reward.domain.point.UserPointHistoryFilter;
import com.personal.marketnote.reward.port.in.result.point.GetUserPointHistoryResult;
import com.personal.marketnote.reward.port.in.usecase.point.GetUserPointHistoryUseCase;
import com.personal.marketnote.reward.port.out.point.FindUserPointHistoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class GetUserPointHistoryService implements GetUserPointHistoryUseCase {
    private final FindUserPointHistoryPort findUserPointHistoryPort;

    @Override
    public GetUserPointHistoryResult getUserPointHistories(Long userId, UserPointHistoryFilter filter) {
        UserPointHistoryFilter targetFilter = FormatValidator.hasValue(filter)
                ? filter
                : UserPointHistoryFilter.ALL;

        return GetUserPointHistoryResult.from(
                findUserPointHistoryPort.findByUserId(userId, targetFilter)
        );
    }
}

