package com.personal.marketnote.user.port.out.authentication;

public interface SaveEmailVerificationCodePort {
    /**
     * @param email            이메일 주소
     * @param verificationCode 인증 코드
     * @param ttlMinutes       인증 코드 유효 시간(분)
     * @Author 성효빈
     * @Date 2026-01-25
     * @Description 인증 코드를 저장합니다.
     */
    void save(String email, String verificationCode, int ttlMinutes);
}
