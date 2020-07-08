package com.itcast.demo.dao;

import com.itcast.demo.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper {

    @Select("select * from sys_user where user_name = #{userName}")
    SysUser getUserByUserName(@Param("userName") String userName);

}
