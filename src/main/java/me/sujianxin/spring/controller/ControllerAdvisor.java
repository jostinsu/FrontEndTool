package me.sujianxin.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/3
 * <p>Time: 23:53
 * <p>Version: 1.0
 */
@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler({NoHandlerFoundException.class, NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle404NotFoundException(Exception exception) {
        ModelAndView model = new ModelAndView("404");
        model.addObject("exception", exception);
        return model;
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class, RuntimeException.class, Exception.class, Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleAllException(Exception exception) {
        ModelAndView model = new ModelAndView("404");
        model.addObject("exception", exception);
        return model;
    }
}
