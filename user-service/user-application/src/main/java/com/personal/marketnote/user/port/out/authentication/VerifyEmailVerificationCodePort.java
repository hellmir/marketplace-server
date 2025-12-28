package com.personal.marketnote.user.port.out.authentication;

public interface VerifyEmailVerificationCodePort {
    boolean verifyAndConsume(String email, String targetCode);
}


