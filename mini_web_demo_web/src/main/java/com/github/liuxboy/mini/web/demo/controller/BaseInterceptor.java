package com.github.liuxboy.mini.web.demo.controller;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuchundong on 2015-03-08 16:32:58
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {

    private final static String charSet = "UTF-8";
    private final static String contentType = "application/json";

    /**
     * Controller之前执行
     * 可以在方法中加一些过滤转发等策略
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding(charSet);
        response.setContentType(contentType);
        return super.preHandle(request, response, handler);
    }

    /**
     * controller之后执行，可用于释放资源等操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

}
