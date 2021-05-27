package cn.util.enums;

/**
 * 功能描述:自定义一个枚举类,进行错误码的统一管理
 */

public enum ResultEnum {

    SUCCESS(0, "操作成功"),
    FAILURE(1, "操作失败"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),
    USER_LOGIN_SUCCESS(0, "登陆成功"),

    /* 业务错误：30001-39999 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "某业务出现问题"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

    /* 数据错误：50001-599999 */
    DATA_NOT_AVAILABLE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),
    QHDM_IS_NOT_CONFIGURE(50004, "该地区暂无数据,或未配置行政区划代码"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限"),
    LOGIN_TIMEOUT(70002, "登录认证失效，请重新登录!"),
    PERMISSION_FAILURE(70003, "获取授权失败，可能登录超时或未授权！"),

    /* 文件错误：80001-89999 */
    FILE_NOT_EXIST(80001, "文件不存在"),
    FILE_PATTERN_ERROR(80002, "文件格式有误"),
    FILE_UPLOAD_FAILURE(80003, "文件上传失败"),
    FILE_DOWNLOAD_ERROR(80004, "文件下载错误"),
    FILE_READ_ERROR(80005, "文件读取错误"),
    FILE_WRITER_ERROR(80006, "文件读取错误");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
