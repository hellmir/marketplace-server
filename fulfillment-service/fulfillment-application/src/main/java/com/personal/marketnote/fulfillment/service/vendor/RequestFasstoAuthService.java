package com.personal.marketnote.fulfillment.service.vendor;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.fulfillment.domain.vendor.FasstoAccessToken;
import com.personal.marketnote.fulfillment.port.in.usecase.vendor.RequestFasstoAuthUseCase;
import com.personal.marketnote.fulfillment.port.out.vendor.RequestFasstoAuthPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED, readOnly = true)
public class RequestFasstoAuthService implements RequestFasstoAuthUseCase {
    private final RequestFasstoAuthPort requestFasstoAuthPort;

    @Override
    public FasstoAccessToken requestAccessToken() {
        return requestFasstoAuthPort.requestAccessToken();
    }
}
