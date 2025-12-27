package com.personal.marketnote.user.port.in.usecase.terms;

import com.personal.marketnote.user.port.in.result.GetTermsResult;
import com.personal.marketnote.user.port.in.result.GetUserTermsResult;

public interface GetTermsUseCase {
    GetTermsResult getAllTerms();

    GetUserTermsResult getUserTerms(Long id);
}
