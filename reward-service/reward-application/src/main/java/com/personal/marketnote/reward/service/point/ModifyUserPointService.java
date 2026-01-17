package com.personal.marketnote.reward.service.point;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.reward.domain.point.*;
import com.personal.marketnote.reward.mapper.RewardCommandToStateMapper;
import com.personal.marketnote.reward.port.in.command.point.ModifyUserPointCommand;
import com.personal.marketnote.reward.port.in.result.point.UpdateUserPointResult;
import com.personal.marketnote.reward.port.in.usecase.point.GetUserPointUseCase;
import com.personal.marketnote.reward.port.in.usecase.point.ModifyUserPointUseCase;
import com.personal.marketnote.reward.port.out.point.SaveUserPointHistoryPort;
import com.personal.marketnote.reward.port.out.point.SaveUserPointPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class ModifyUserPointService implements ModifyUserPointUseCase {
    private final GetUserPointUseCase getUserPointUseCase;
    private final SaveUserPointPort saveUserPointPort;
    private final SaveUserPointHistoryPort saveUserPointHistoryPort;

    @Override
    public UpdateUserPointResult modify(ModifyUserPointCommand command) {
        UserPoint userPoint = getUserPointUseCase.getUserPoint(command.userId());

        long updatedAmount = calculateNewAmount(command, userPoint);
        UserPoint saved = saveUserPointPort.save(userPoint.withAmount(updatedAmount));

        LocalDateTime accumulatedAt = LocalDateTime.now();
        saveUserPointHistoryPort.save(
                UserPointHistory.from(
                        RewardCommandToStateMapper.mapToUserPointHistoryCreateState(command, accumulatedAt)
                )
        );

        return UpdateUserPointResult.from(saved);
    }

    private long calculateNewAmount(ModifyUserPointCommand command, UserPoint userPoint) {
        PointAmount current = PointAmount.of(userPoint.getAmount());
        long delta = command.changeType() == UserPointChangeType.DEDUCTION
                ? -Math.abs(command.amount())
                : Math.abs(command.amount());

        try {
            return current.add(delta);
        } catch (NegativeUserPointAmountException e) {
            throw new NegativeUserPointAmountException(current.getValue() + delta);
        }
    }
}
