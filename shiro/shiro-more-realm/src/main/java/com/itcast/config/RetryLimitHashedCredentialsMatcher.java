package com.itcast.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * 功能描述：免密登陆
 * 通过继承HashedCredentialsMatcher，重写其中的doCredentialsMatch方法
 * 相当于重新写了密码匹配器
 *
 * @authro JIAQI
 * @date 2019/11/15 - 15:06
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        CustomLoginToken token = (CustomLoginToken) authcToken;
        //如果是免密登录直接返回true跳过密码验证
        if (!token.isPassword()) {
            return true;
        }
        //不是免密登录，调用父类的方法
        return super.doCredentialsMatch(token, info);
    }
}
