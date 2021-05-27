package cn.util.exception;

import cn.util.enums.ResultEnum;

/**
 * 自定义异常
 */
public class BDException extends RuntimeException {
	private Integer code;

	public BDException(ResultEnum resultEnum) {
		super(resultEnum.getMsg());
		this.code = resultEnum.getCode();
	}

	//新增msg  进行自定义异常消息
	public BDException(ResultEnum resultEnum, String msg) {
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
