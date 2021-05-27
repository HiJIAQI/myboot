package com.itcast.demo.entity;

import javax.validation.constraints.NotNull;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/8/7 - 14:07
 */
public class SysUser {

    @NotNull(message = "用户id不能为空!")
    private Long userId;

    @NotNull(message = "用户名不能为空!")
    private String userName;

    @NotNull(message = "密码不能为空!")
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
