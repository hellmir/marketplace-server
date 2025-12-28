package com.personal.marketnote.user.port.out.authentication;

public interface SaveEmailVerificationCodePort {
    void save(String email, String verificationCode, int ttlMinutes);
}
