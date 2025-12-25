package com.personal.shop.common.utility.http.cookie;

public interface HttpCookieObjectFactory {
    HttpCookieObject create(
            HttpCookieName httpCookieName, String value, String domain, long cookieAge, boolean httpOnly
    );
}
