package com.itcast.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 用户认证
 */
public class AppRealm extends AuthorizingRealm {
    //    @Autowired
//    PcUserService pcUserService;
//
//    @Override
//    public String getName() {
//        return LoginType.PC_USER;
//    }
//
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
//        if (!pc.getRealmNames().contains(getName())) return null;
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return null;
    }

    // 认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        //验证账号密码
//        CustomLoginToken token = (CustomLoginToken) authenticationToken;
//        WechatUser pcUser = pcUserService.getUserByUserName(token.getUsername());
//        if (pcUser == null) {
//            return null;
//        }
//
//        //最后的比对需要交给安全管理器
//        //三个参数进行初步的简单认证信息对象的包装
//        ByteSource credentialsSalt = ByteSource.Util.bytes(pcUser.getPhone());
//        AuthenticationInfo info = new SimpleAuthenticationInfo(pcUser, pcUser.getPassword(), credentialsSalt,
//                this.getClass().getSimpleName());
        return null;
    }

}
