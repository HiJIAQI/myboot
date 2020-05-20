package com.itcast.entity;

import lombok.Data;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/5/19 - 11:05
 */
@Data
public class SysRoleMenu {

    /* 角色菜单关联id */
    private Long id;

    /* 角色id */
    private Long roleId;

    /* 菜单id */
    private Long menuId;

}
