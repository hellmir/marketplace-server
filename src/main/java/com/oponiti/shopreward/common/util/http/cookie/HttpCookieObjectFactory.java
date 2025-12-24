package com.oponiti.shopreward.common.util.http.cookie;

public interface HttpCookieObjectFactory {
    HttpCookieObject create(
            HttpCookieName httpCookieName, String value, String domain, long cookieAge, boolean httpOnly
    );
}
