package com.personal.marketnote.user.adapter.in.client.user.response;

public record SignInResponse(
        String accessToken,
        String refreshToken,
        boolean isRequiredTermsAgreed
) {
    public static SignInResponse of(String accessToken, String refreshToken, boolean isRequiredTermsAgreed) {
        return new SignInResponse(accessToken, refreshToken, isRequiredTermsAgreed);
    }
}
