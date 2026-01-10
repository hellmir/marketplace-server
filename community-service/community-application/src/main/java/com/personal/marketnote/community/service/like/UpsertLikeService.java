package com.personal.marketnote.community.service.like;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.like.Like;
import com.personal.marketnote.community.exception.LikeNotFoundException;
import com.personal.marketnote.community.port.in.command.like.UpsertLikeCommand;
import com.personal.marketnote.community.port.in.result.like.UpsertLikeResult;
import com.personal.marketnote.community.port.in.usecase.like.GetLikeUseCase;
import com.personal.marketnote.community.port.in.usecase.like.UpsertLikeUseCase;
import com.personal.marketnote.community.port.out.like.SaveLikePort;
import com.personal.marketnote.community.port.out.like.UpdateLikePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class UpsertLikeService implements UpsertLikeUseCase {
    private final GetLikeUseCase getLikeUseCase;
    private final SaveLikePort saveLikePort;
    private final UpdateLikePort updateLikePort;

    @Override
    public UpsertLikeResult upsertLike(UpsertLikeCommand command) {
        try {
            Like like = getLikeUseCase.getLike(command.targetType(), command.targetId(), command.userId());
            like.revert();
            updateLikePort.update(like);

            return UpsertLikeResult.from(like, false);
        } catch (LikeNotFoundException lnfe) {
            Like like = Like.of(command.targetType(), command.targetId(), command.userId());
            saveLikePort.save(like);

            return UpsertLikeResult.from(like, true);
        }
    }
}
