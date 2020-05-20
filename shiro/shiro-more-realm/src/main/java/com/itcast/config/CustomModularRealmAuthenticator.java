package com.itcast.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 功能描述：重写认证策略进行不同登陆类型认证器分发
 * 目的：做多用户realm
 *
 * @author JIAQI
 * @date 2019/11/13 - 17:21
 */
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 判断getRealms()是否返回为空
        //assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        CustomLoginToken token = (CustomLoginToken) authenticationToken;
        // 获取登录人的登录类型
        String loginType = token.getLoginType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 找到登录类型对应的指定Realm
        Collection<Realm> typeRealms = new ArrayList<Realm>();
        for (Realm realm : realms) {
            // 通过对比登陆类型做对应的realm认证跳转
            if (realm.getName().contains(loginType))
                typeRealms.add(realm);
        }

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1)
            return doSingleRealmAuthentication(typeRealms.iterator().next(), token);
        else
            return doMultiRealmAuthentication(typeRealms, token);
    }

}
