package me.sujianxin.spring.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/16
 * <p>Time: 19:10
 * <p>Version: 1.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("in LoginInterceptor");
        request.getSession().setAttribute("userid", 1);
        request.getSession().setAttribute("adminid", 1);
        request.getSession().setAttribute("mail", "751611201@qq.com");
        return super.preHandle(request, response, handler);
    }
}
