package cn.util.exception;

import cn.util.Result;
import cn.util.enums.ResultEnum;
import cn.util.http.HttpServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 */
@RestControllerAdvice
@Slf4j
public class BDExceptionHandler {

    // 参数验证异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ParameterException(Exception e, HttpServletRequest request) {
        MissingServletRequestParameterException exception = (MissingServletRequestParameterException) e;
        log.error("【参数验证异常】{}", exception.getMessage());
        if (HttpServletUtils.jsAjax(request)) {
            return Result.error(ResultEnum.PARAM_IS_BLANK);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("message", ResultEnum.PARAM_IS_BLANK.message());
        return modelAndView;
    }

    // 文件上传下载全局异常处理
    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object handleFileException(Exception e, HttpServletRequest request) {
        FileException exception = (FileException) e;
        log.error("【文件上传下载异常】{}", exception.getMessage());
        if (HttpServletUtils.jsAjax(request)) {
            return Result.error(exception.getCode(), exception.getMessage());
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public Object handleException(Exception e, HttpServletRequest request) {
        log.error("【全局异常捕获】{}" + e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView();
        Result result = new Result();
        //如果异常为自定义异常就走自定义异常
        if (e instanceof BDException) {
            BDException girlException = (BDException) e;
            log.error("【自定义异常】{}", girlException.getMessage());
            return Result.error(girlException.getCode(), girlException.getMessage());
        } else {
            modelAndView.addObject("message", "服务器错误，请联系管理员！");
            result = Result.error(500, "服务器错误，请联系管理员！");
        }
        if (HttpServletUtils.jsAjax(request)) {
            return result;
        }
        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}
