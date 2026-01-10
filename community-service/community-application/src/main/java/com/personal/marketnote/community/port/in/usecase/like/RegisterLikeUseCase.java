package com.personal.marketnote.community.port.in.usecase.like;

import com.personal.marketnote.community.port.in.command.like.RegisterLikeCommand;
import com.personal.marketnote.community.port.in.result.like.RegisterLikeResult;

public interface RegisterLikeUseCase {
    RegisterLikeResult registerLike(RegisterLikeCommand command);
}
