package com.itcast.entity;

import lombok.Data;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/5/19 - 10:57
 */
@Data
public class SysUser {

    /* 用户id */
    private Long userId;

    /* 用户名 */
    private String userName;

    /* 密码 */
    private String password;

    /* 盐值 */
    private String salt;
}
