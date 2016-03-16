package com.mrlu.sven.controller.interceptor;

import com.alibaba.fastjson.JSON;
import com.mrlu.sven.common.HandleModelResult;
import com.mrlu.sven.controller.util.ContextUtil;
import com.mrlu.sven.controller.util.InterceptorUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stefan on 16-3-16.
 */
public class AccountInterceptor implements HandlerInterceptor {

    Logger logger = Logger.getLogger(AccountInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestServletPath = request.getServletPath();
        logger.info("request path: " + requestServletPath);
        for (String interceptorUrl: InterceptorUtil.INTERCEPTOR_URLS){
            Pattern pattern = Pattern.compile(interceptorUrl);
            Matcher matcher = pattern.matcher(requestServletPath);
            if(matcher.find()){
                if(ContextUtil.validCookie(request) == null){
                    logger.info("request not auth: " + requestServletPath);
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getOutputStream().write(JSON.toJSONString(HandleModelResult.return401ModelResult()).getBytes("utf-8"));
                    response.flushBuffer();
                    return false;
                }
            }
        }
        logger.info("request already auth: " + requestServletPath);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
