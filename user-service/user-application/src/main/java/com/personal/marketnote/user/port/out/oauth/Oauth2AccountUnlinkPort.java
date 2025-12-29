package com.personal.marketnote.user.port.out.oauth;

import com.personal.marketnote.user.service.exception.UnlinkOauth2AccountFailedException;

/**
 * 외부 OAuth2 API와의 연결 해제를 위한 아웃바운드 포트
 */
public interface Oauth2AccountUnlinkPort {
    /**
     * 사용자의 카카오 액세스 토큰으로 카카오 계정 연결 해제
     */
    void unlinkKakaoAccount(String oidcId) throws UnlinkOauth2AccountFailedException;

    /**
     * 구글 토큰 취소 API로 연결 해제 (access token 또는 refresh token)
     */
    void unlinkGoogleAccount(String accessToken) throws UnlinkOauth2AccountFailedException;
}
