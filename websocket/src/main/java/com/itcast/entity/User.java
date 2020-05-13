package com.itcast.entity;

import java.security.Principal;

/**
 * 功能描述：
 *
 * @authro JIAQI
 * @date 2019/7/10 - 13:55
 */
public final class User implements Principal {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}