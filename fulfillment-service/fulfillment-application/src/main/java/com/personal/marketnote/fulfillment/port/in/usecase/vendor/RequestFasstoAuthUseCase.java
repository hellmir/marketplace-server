package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

import com.personal.marketnote.fulfillment.domain.vendor.FasstoAccessToken;

public interface RequestFasstoAuthUseCase {
    /**
     * @return Fassto access token {@link FasstoAccessToken}
     * @Date 2026-01-23
     * @Author 성효빈
     * @Description Request a Fassto access token.
     */
    FasstoAccessToken requestAccessToken();
}
