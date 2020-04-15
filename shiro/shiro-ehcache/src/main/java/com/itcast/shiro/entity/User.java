package com.itcast.shiro.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/14 - 9:18
 */
public class User implements Serializable {
    private String username;
    private String password;
    private String salt;
    private Boolean available;
    private String role;
    private List<String> permissions;

    public User(String username, String password, String salt, Boolean available, String role, List<String> permissions) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.available = available;
        this.role = role;
        this.permissions = permissions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取MD5盐  这里用username+salt实现
     * @return
     */
    public String getSalt() {
        return username+salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", available=" + available +
                ", role='" + role + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}

