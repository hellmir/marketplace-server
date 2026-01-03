package com.personal.marketnote.user.adapter.in.client.user.response;

public record SignInResponse(
        String accessToken,
        boolean isRequiredTermsAgreed
) {
    public static SignInResponse of(String accessToken, boolean isRequiredTermsAgreed) {
        return new SignInResponse(accessToken, isRequiredTermsAgreed);
    }
}
