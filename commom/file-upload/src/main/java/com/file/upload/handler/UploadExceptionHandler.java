package com.file.upload.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 功能描述：全局异常处理
 *
 * @author JIAQI
 * @date 2020/4/16 - 9:25
 */
@ControllerAdvice
public class UploadExceptionHandler {

    /* spring默认上传大小1MB 超出大小捕获异常MaxUploadSizeExceededException */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return "文件大小超出1MB限制, 请压缩或降低文件质量! ";
    }
}
