package com.itcast.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itcast.entity.UserEntity;
import com.itcast.mapper.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<UserEntity> findUser() {
        return userMapper.findUser();
    }

    public Integer insertUser(String userName) {
        return userMapper.insertUser(userName);
    }

}
