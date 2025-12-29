package com.personal.marketnote.user.port.in.usecase.user;

import com.personal.marketnote.user.port.in.command.SignInCommand;
import com.personal.marketnote.user.port.in.result.SignInResult;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

public interface SignInUseCase {
    /**
     * @param signInCommand 로그인 명령
     * @param authVendor    인증 제공자
     * @param oidcId        외부 인증 ID
     * @return 로그인 결과 {@link SignInResult}
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 네이티브 로그인 또는 소셜 로그인을 수행합니다.
     */
    SignInResult signIn(SignInCommand signInCommand, AuthVendor authVendor, String oidcId, String ipAddress);
}
