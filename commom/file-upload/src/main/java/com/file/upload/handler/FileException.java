package com.file.upload.handler;

import com.file.upload.enums.ResultEnum;

/**
 * 功能描述：自定义文件异常
 *
 * @author JIAQI
 * @date 2020/4/20 - 16:06
 */
public class FileException extends RuntimeException {

    private Integer code;

    public FileException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    //新增msg  进行自定义异常消息
    public FileException(ResultEnum resultEnum, String msg) {
        super(msg);
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
