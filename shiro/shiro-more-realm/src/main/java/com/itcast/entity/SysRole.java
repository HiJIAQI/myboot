package com.itcast.entity;

import lombok.Data;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/5/19 - 10:58
 */
@Data
public class SysRole {
    /* 角色id */
    private Long roleId;

    /* 角色名称 */
    private String role_name;

    /* 角色标识 */
    private String role_sign;

    /* 备注 */
    private String remark;

    /* 创建时间 */
    private String create_time;

    /* 更新时间 */
    private String update_time;

}
