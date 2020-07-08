package com.itcast.demo.dao;

import com.itcast.demo.entity.SysRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper {

    @Select("SELECT r.role_sign FROM sys_user_role ur LEFT JOIN sys_role r ON ur.role_id = r.id \n" +
            "WHERE ur.user_id = #{userId}")
    List<SysRole> findRoleSignByUserId(int userId);
}
