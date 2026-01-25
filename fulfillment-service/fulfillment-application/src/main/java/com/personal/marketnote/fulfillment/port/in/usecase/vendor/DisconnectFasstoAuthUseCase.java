package com.personal.marketnote.fulfillment.port.in.usecase.vendor;

public interface DisconnectFasstoAuthUseCase {
    /**
     * @param accessToken Fassto access token
     * @Date 2026-01-25
     * @Author 성효빈
     * @Description Disconnect Fassto access token.
     */
    void disconnectAccessToken(String accessToken);
}
