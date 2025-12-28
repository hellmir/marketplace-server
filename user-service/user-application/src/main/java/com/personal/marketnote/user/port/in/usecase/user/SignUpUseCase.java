package com.personal.marketnote.user.port.in.usecase.user;

import com.personal.marketnote.user.port.in.command.SignUpCommand;
import com.personal.marketnote.user.port.in.result.SignUpResult;
import com.personal.marketnote.user.security.token.vendor.AuthVendor;

public interface SignUpUseCase {
    /**
     * @param signUpCommand 회원 등록 요청 커맨드
     * @param authVendor    인증 제공자
     * @param oidcId        외부 인증 ID
     * @return 회원 가입 결과 {@link SignUpResult}
     * @Author 성효입
     * @Date 2025-12-28
     * @Description 회원으로 가입합니다.
     */
    SignUpResult signUp(SignUpCommand signUpCommand, AuthVendor authVendor, String oidcId);
}
