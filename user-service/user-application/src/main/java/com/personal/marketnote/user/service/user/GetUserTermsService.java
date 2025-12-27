package com.personal.marketnote.user.service.user;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.user.port.in.result.GetTermsResult;
import com.personal.marketnote.user.port.in.usecase.GetUserTermsUseCase;
import com.personal.marketnote.user.port.out.GetUserTermsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_UNCOMMITTED, readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class GetUserTermsService implements GetUserTermsUseCase {
    private final GetUserTermsPort getUserTermsPort;

    @Override
    public GetTermsResult getAllTerms() {
        return GetTermsResult.from(getUserTermsPort.getAllTerms());
    }
}
