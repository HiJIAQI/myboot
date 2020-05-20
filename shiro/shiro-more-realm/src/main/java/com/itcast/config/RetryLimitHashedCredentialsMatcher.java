package com.itcast.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * 功能描述：自定义密码匹配器
 * 通过继承HashedCredentialsMatcher，重写其中的doCredentialsMatch方法
 * shiro中的密码对比流程：doCredentialsMatch()->SimpleCredentialsMatcher
 * ->equals()->MessageDigest类中的isEqual()方法
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
