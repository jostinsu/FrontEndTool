package me.sujianxin.spring.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/2/2
 * <p>Time: 21:04
 * <p>Version: 1.0
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception exception) {
        System.out.println("Exception Message:" + exception.getMessage());
        //if (request.getServletPath().toLowerCase().startsWith("/admin")) {
        //    // exception.printStackTrace();
        //    Map<String, Object> model = new HashMap<String, Object>();
        //    model.put("ex", exception);
        //    return new ModelAndView("b_page/error", model);
        //} else {
        //    return new ModelAndView("f_page/500");
        //}
        return null;
    }
}
