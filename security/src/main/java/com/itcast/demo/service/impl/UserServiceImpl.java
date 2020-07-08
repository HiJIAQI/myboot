package com.itcast.demo.service.impl;

import com.itcast.demo.dao.SysRoleMapper;
import com.itcast.demo.dao.SysUserMapper;
import com.itcast.demo.entity.SysRole;
import com.itcast.demo.entity.SysUser;
import com.itcast.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/7/2 - 16:56
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;

    /**
     * 进行登陆认证操作
     *
     * @param username 用户名
     * @return security内部所需要的UserDetails对象
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        // 获取用户信息
        SysUser sysUser = sysUserMapper.getUserByUserName(username);
        if (Objects.nonNull(sysUser)) {
            // 获取用户角色信息
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            List<SysRole> role = sysRoleMapper.findRoleSignByUserId(sysUser.getId());
            for (SysRole sysRole : role) {
                authorities.add(new SimpleGrantedAuthority(sysRole.getRoleSign()));
            }
            userDetails = new User(sysUser.getUserName(),
                    sysUser.getPassword(), authorities);
        }
        return userDetails;
    }
}
