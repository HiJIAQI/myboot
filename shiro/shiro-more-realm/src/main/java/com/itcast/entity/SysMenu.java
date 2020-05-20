package com.itcast.entity;

import lombok.Data;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/5/19 - 11:00
 */
@Data
public class SysMenu {
    /* 菜单id */
    private Long menuId;

    /* 菜单名称 */
    private String menuName;

    /* 请求地址 */
    private String url;

    /* 菜单权限 */
    private String perms;

    /* 菜单类型（M目录 C菜单 F按钮） */
    private String menuType;

    /* 菜单图标 */
    private String icon;

    /* 排序 */
    private Long orderNum;

    /* 是否展开 （true：展开，false：不展开） */
    private String spread;

    /* 父菜单ID，一级菜单为0 */
    private Long parentId;

    /* 创建时间 */
    private String createTime;

    /* 更新时间 */
    private String updateTime;

}
