package com.personal.marketnote.common.utility;

public class ApiConstant {
    public static final String USER_ID_KEY = "userId";
    public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final String ADMIN_POINTCUT = "isAuthenticated() and hasRole('ROLE_ADMIN')";
    public static final String ADMIN_OR_SELLER_POINTCUT = "isAuthenticated() and hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')";
}
