package com.personal.marketnote.community.service.like;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.like.Like;
import com.personal.marketnote.community.port.in.command.like.RegisterLikeCommand;
import com.personal.marketnote.community.port.in.result.like.RegisterLikeResult;
import com.personal.marketnote.community.port.in.usecase.like.RegisterLikeUseCase;
import com.personal.marketnote.community.port.out.like.SaveLikePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class RegisterLikeService implements RegisterLikeUseCase {
    private final SaveLikePort saveLikePort;

    @Override
    public RegisterLikeResult registerLike(RegisterLikeCommand command) {
        if (saveLikePort.existsByTarget(command.targetType(), command.targetId(), command.userId())) {
            return RegisterLikeResult.from(
                    Like.of(command.targetType(), command.targetId(), command.userId())
            );
        }

        Like saved = saveLikePort.save(
                Like.of(command.targetType(), command.targetId(), command.userId())
        );
        return RegisterLikeResult.from(saved);
    }
}
