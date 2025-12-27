package com.personal.marketnote.user.service.terms;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.port.in.result.GetTermsResult;
import com.personal.marketnote.user.port.in.usecase.terms.GetTermsUseCase;
import com.personal.marketnote.user.port.out.user.FindTermsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_UNCOMMITTED, readOnly = true)
public class GetTermsService implements GetTermsUseCase {
    private final FindTermsPort findTermsPort;

    @Override
    public GetTermsResult getAllTerms() {
        return GetTermsResult.from(findTermsPort.findAll());
    }
}
