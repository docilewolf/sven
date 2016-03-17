package com.mrlu.sven.controller.util;

import com.mrlu.sven.domain.Account;
import com.mrlu.sven.service.util.AccountUtil;
import org.apache.commons.collections.MapUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * ContextUtil
 * Created by stefan on 16-3-16.
 */
public class ContextUtil {

    private static Map<String, Account> cookieMap = new HashMap<>();
    private static final String cookieName = "jsid";

    /**
     * 设置登陆cookie
     * @param account
     * @param response
     */
    public static boolean setCookie(Account account, HttpServletResponse response){
        String sessionId = AccountUtil.generateSessionId(account);
        Cookie cookie = new Cookie(cookieName,sessionId);
        //cookie存在于会话期
        cookie.setMaxAge(-1);
        //所有路径
        cookie.setPath("/");
        response.addCookie(cookie);

        //缓存cookie
        if(MapUtils.isNotEmpty(cookieMap)) {
            for (String jsid : cookieMap.keySet()) {
                Account cacheAccount = cookieMap.get(jsid);
                if (cacheAccount.getUserName().equals(account.getUserName())) {
                    cookieMap.remove(jsid);
                    cookieMap.put(sessionId, account);
                    break;
                }
            }
        }else{
            cookieMap.put(sessionId, account);
        }
        return true;
    }

    /**
     * 删除cookie
     * @param request
     */
    public static boolean rmCookie(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = getJsidCookie(request);
        if(null != cookie && cookieMap.get(cookie.getValue()) != null){
            cookieMap.remove(cookie.getValue());
            //删除浏览器cookie
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return true;
    }

    /**
     * cookie认证
     * @param request
     * @return
     */
    public static Account validCookie(HttpServletRequest request){
        Cookie cookie = getJsidCookie(request);
        if(null != cookie){
            return cookieMap.get(cookie.getValue());
        }
        return null;
//        return null != cookie && cookieMap.get(cookie.getValue()) != null;
    }

    private static Cookie getJsidCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if(null == cookies) return null;
        for(Cookie cookie: cookies){
            if(cookieName.equals(cookie.getName())){
                return cookie;
            }
        }
        return null;
    }
}
