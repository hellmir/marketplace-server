package com.personal.marketnote.reward.service.point;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.reward.domain.point.UserPoint;
import com.personal.marketnote.reward.domain.point.UserPointHistory;
import com.personal.marketnote.reward.mapper.RewardCommandToStateMapper;
import com.personal.marketnote.reward.port.in.command.point.ModifyUserPointCommand;
import com.personal.marketnote.reward.port.in.result.point.UpdateUserPointResult;
import com.personal.marketnote.reward.port.in.usecase.point.GetUserPointUseCase;
import com.personal.marketnote.reward.port.in.usecase.point.ModifyUserPointUseCase;
import com.personal.marketnote.reward.port.out.point.SaveUserPointHistoryPort;
import com.personal.marketnote.reward.port.out.point.SaveUserPointPort;
import com.personal.marketnote.reward.port.out.point.UpdateUserPointPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class ModifyUserPointService implements ModifyUserPointUseCase {
    private final GetUserPointUseCase getUserPointUseCase;
    private final SaveUserPointPort saveUserPointPort;
    private final UpdateUserPointPort updateUserPointPort;
    private final SaveUserPointHistoryPort saveUserPointHistoryPort;

    @Override
    public UpdateUserPointResult modify(ModifyUserPointCommand command) {
        UserPoint userPoint = getUserPointUseCase.getUserPoint(command.userId());

        userPoint.changeAmount(command.isAccrual(), command.amount());
        UserPoint updatedPoint = updateUserPointPort.update(userPoint);

        saveUserPointHistoryPort.save(
                UserPointHistory.from(
                        RewardCommandToStateMapper.mapToUserPointHistoryCreateState(command, updatedPoint.getModifiedAt())
                )
        );

        return UpdateUserPointResult.from(updatedPoint);
    }
}
