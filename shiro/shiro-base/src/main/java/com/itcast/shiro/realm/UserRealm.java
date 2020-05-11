package com.itcast.shiro.realm;

import com.itcast.shiro.constant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * 功能描述：定义一个自定义的用户授权
 *
 * @author JIAQI
 * @date 2020/4/13 - 11:02
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    /* 授权 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.err.println("授权");
        // 获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        // 获取角色
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        // 获取权限
        Set<String> permissions = new HashSet<>();
        permissions.add("sys:save");
        // 将相应的set数据添加到授权信息中
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    /* 认证 */
    // SimpleCredentialsMatcher ->doCredentialsMatch()
    // -> MessageDigest -> isEqual
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.err.println("认证");
        // 验证账号密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // TODO 模拟数据库获取数据
        // 1.根据用户名获取用户并判断用户是否存在
        if (!UserConstant.USER.getUserName().equals(token.getUsername())) {
            throw new UnknownAccountException();
        }
        // 将账号密码交给安全管理器进行最后的比对需要
        // 用户名  密码(这里是指从数据库中获取的password) 当前realm的名称(也可以使用“”替代)
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getUsername(), UserConstant.USER.getPassWord(), getName());
        // 进行加盐
        info.setCredentialsSalt(ByteSource.Util.bytes(token.getUsername()));
        return info;
    }

    //设置加密方式为MD5
    /*@Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName("MD5");
        shaCredentialsMatcher.setHashIterations(1);
        shaCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }*/

}
