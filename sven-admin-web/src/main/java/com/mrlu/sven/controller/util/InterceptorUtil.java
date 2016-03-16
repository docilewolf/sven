package com.mrlu.sven.controller.util;

/**
 * Created by stefan on 16-3-16.
 */
public class InterceptorUtil {
    public static final String LOGIN_URL = "/admin/account/login";
    public static final String LOGOUT_URL = "/admin/account/logout";
    public static final String REGISTER_URL = "/admin/account/register";
    public static final String[] INTERCEPTOR_URLS = new String[]{"[a-zA-Z]*save*", "[a-zA-Z]*update*"};
}
