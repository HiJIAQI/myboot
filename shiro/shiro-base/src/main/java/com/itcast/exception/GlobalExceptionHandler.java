package com.itcast.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView handleShiroException(Exception e) {
        return new ModelAndView("unauthorized");
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.OK)
    public String AuthorizationException(Exception e) {
        return "权限认证失败";
    }

}
