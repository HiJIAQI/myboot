package com.itcast.util;


/**
 * 功能描述:http请求返回的最外层对象
 */
public class Result {

    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体的内容.
     */
    private Object data;

    /**
     * 分页总条数.
     */
    private Long count;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public void setResultCode(ResultEnum code) {
        this.code = code.code();
        this.msg = code.message();
    }

    //成功 不返回数据直接返回成功信息
    public static Result success() {
        return success(null);
    }

    //成功 并且加上返回数据
    public static Result success(Object data) {
        return success(ResultEnum.SUCCESS, data);
    }

    //成功 自定义成功返回状态 加上数据
    public static Result success(ResultEnum resultEnum, Object data) {
        Result result = new Result();
        result.setResultCode(resultEnum);
        result.setData(data);
        return result;
    }
    public static Result success(ResultEnum resultEnum, Object data,String msg) {
        Result result = new Result();
        result.setResultCode(resultEnum);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    // Layui分页成功返回对象
    public static Result pageSuccess(Object data, Long count) {
        Result result = new Result();
        result.setResultCode(ResultEnum.SUCCESS);
        result.setData(data);
        result.setCount(count);
        return result;
    }

    // 单返回失败的状态码
    public static Result failure(ResultEnum resultEnum) {
        return failure(resultEnum, null);
    }

    // 返回失败的状态码 及 数据
    public static Result failure(ResultEnum resultEnum, Object data) {
        Result result = new Result();
        result.setResultCode(resultEnum);
        result.setData(data);
        return result;
    }

    //进行自定义异常消息 覆盖原始定义消息
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return result;
    }

}
