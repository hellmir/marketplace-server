package com.personal.marketnote.user.port.in.usecase.terms;

import com.personal.marketnote.user.port.in.command.AcceptOrCancelTermsCommand;
import com.personal.marketnote.user.port.in.result.UpdateUserTermsResult;

public interface UpdateTermsUseCase {
    UpdateUserTermsResult acceptOrCancelTerms(Long userId, AcceptOrCancelTermsCommand acceptOrCancelTermsCommand);
}
