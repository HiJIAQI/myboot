package com.itcast.config;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 功能描述：实现多realm的关键在于继承shiro默认的UsernamePasswordToken,
 * 添加一个字段用于标识不同的realm,用户登录的时候带上标识的字段，
 * shiro则根据字段去不同的realm去验证登陆，授权等.
 *
 * @authro JIAQI
 * @date 2019/11/13 - 16:22
 */
public class CustomLoginToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 2020457391511655213L;

    private String loginType;

    private boolean isPassword = true;

    public CustomLoginToken() {
    }

    /**
     * 免密登陆
     *
     * @param username 用户名
     */
    public CustomLoginToken(final String username, final String loginType) {
        super(username, "");
        this.loginType = loginType;
        this.isPassword = false;
    }

    /**
     * 正常流程登陆
     *
     * @param username  用户名
     * @param password  密码
     * @param loginType 登陆类型（PC端用户 单位用户）
     */
    public CustomLoginToken(final String username, final String password,
                            final String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public boolean isPassword() {
        return isPassword;
    }

    public void setPassword(boolean password) {
        isPassword = password;
    }
}
