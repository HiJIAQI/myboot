package com.itcast.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.itcast.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Select("select * from user_info")
    List<UserEntity> findUser();

    @Insert("insert into user_info (user_name) values (#{userName})")
    int insertUser(@Param("userName") String userName);

}
