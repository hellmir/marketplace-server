package com.personal.marketnote.community.port.in.usecase.like;

import com.personal.marketnote.community.port.in.command.like.UpsertLikeCommand;
import com.personal.marketnote.community.port.in.result.like.UpsertLikeResult;

public interface UpsertLikeUseCase {
    UpsertLikeResult upsertLike(UpsertLikeCommand command);
}
