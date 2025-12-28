package com.personal.marketnote.user.port.in.usecase.authentication;

public interface SendEmailVerificationUseCase {
    /**
     * @param email 이메일 주소
     * @Author 성효빈
     * @Date 2025-12-28
     * @Description 이메일 인증 요청을 전송합니다.
     */
    void sendEmailVerification(String email);
}
