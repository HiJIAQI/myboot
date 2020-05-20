package com.itcast.realm;

import com.itcast.config.CustomLoginToken;
import com.itcast.config.LoginType;
import com.itcast.dao.SysUserMapper;
import com.itcast.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PcRealm extends AuthorizingRealm {

    @Autowired
    SysUserMapper sysUserMapper;

    // 此处必须对CachingRealm类中的getName方法进行重写否在无法对realm类型进行判断
    @Override
    public String getName() {
        return LoginType.PC_USER;
    }

    /**
     * 授权流程
     * 1.根据用户user->2.获取角色id->3.根据角色id获取权限permission
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        log.error("PC端用户shiror授权");
        if (!pc.getRealmNames().contains(getName())) return null;
        SysUser user = (SysUser) pc.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取permission标识
        info.addStringPermission("*:*:*");
        // 获取角色标识
        info.addRole("admin");
        return info;
    }

    //
    // 认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.error("PC端用户shiror登陆认证");
        //验证账号密码
        CustomLoginToken token = (CustomLoginToken) authenticationToken;
        // 获取前端用户所输入的用户名
        String username = token.getUsername();
        // 根据用户名获取所对应的用户
        SysUser user = sysUserMapper.findUserByUserName(username);
        if (user == null) {
            return null;
        }
        // 最后的比对需要交给安全管理器
        // 三个参数进行初步的简单认证信息对象的包装
        // 所属账号的盐
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt,
                this.getClass().getSimpleName());
    }
}
