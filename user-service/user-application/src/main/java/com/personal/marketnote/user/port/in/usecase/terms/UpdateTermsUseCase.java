package com.personal.marketnote.user.port.in.usecase.terms;

import com.personal.marketnote.user.port.in.command.AcceptOrCancelTermsCommand;
import com.personal.marketnote.user.port.in.result.GetUserTermsResult;

public interface UpdateTermsUseCase {
    GetUserTermsResult acceptOrCancelTerms(Long userId, AcceptOrCancelTermsCommand acceptOrCancelTermsCommand);
}
