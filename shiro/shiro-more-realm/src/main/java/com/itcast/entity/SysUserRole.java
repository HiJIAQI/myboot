package com.itcast.entity;

import lombok.Data;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/5/19 - 11:03
 */
@Data
public class SysUserRole {

    /* 用户角色关联id */
    private Long id;

    /* 用户id */
    private Long userId;

    /* 角色id */
    private Long roleId;

    /* 创建时间 */
    private String createTime;

    /* 更新时间 */
    private String updateTime;

}
