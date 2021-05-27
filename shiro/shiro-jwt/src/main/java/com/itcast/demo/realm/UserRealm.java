package com.itcast.demo.realm;

import com.itcast.demo.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 功能描述：定义一个自定义的用户授权
 *
 * @author JIAQI
 * @date 2020/4/13 - 11:02
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    // 指定当前 authenticationToken 需要为 JwtToken 的实例
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        System.out.println(jwtToken.getCredentials());
        System.out.println(jwtToken.getPrincipal());
        return new SimpleAuthenticationInfo(jwtToken.getPrincipal(),
                jwtToken.getCredentials(), getName());
    }

}
