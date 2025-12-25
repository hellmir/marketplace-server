package com.personal.shopreward.common.util.http.cookie;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;

@RequiredArgsConstructor
class SpringResponseCookieHttpCookieObject implements HttpCookieObject {
    private final ResponseCookie responseCookie;

    @Override
    public String asSetCookieHeaderValue() {
        return this.responseCookie.toString();
    }
}
