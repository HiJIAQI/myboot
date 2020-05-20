package com.itcast.dao;

import com.itcast.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/5/19 - 11:07
 */
@Repository
public interface SysUserMapper {

    @Select("SELECT * FROM sys_user WHERE  user_name= #{sysUser.userName} AND password= #{sysUser.password}")
    SysUser findByUser(@Param("sysUser") SysUser sysUser);

    @Select("SELECT * FROM sys_user WHERE  user_name= #{userName}")
    SysUser findUserByUserName(@Param("userName") String userName);

}
