package com.itcast.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 单位认证
 */
public class PcRealm extends AuthorizingRealm {
    //    private static Logger logger = LoggerFactory.getLogger(UnitUserReaml.class);
//
//    @Autowired
//    UnitUserService unitUserService;
//
//    @Override
//    public String getName() {
//        return LoginType.UNIT_USER;
//    }
//
//    /**
//     * 授权流程
//     * 1.根据用户user->2.获取角色id->3.根据角色id获取权限permission
//     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
//
//        if (!pc.getRealmNames().contains(getName())) return null;
//        //方法一：获得user对象
//        UserT user = (UserT) pc.getPrimaryPrincipal();
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        //获取permission
//        if (user.getUserid() == 1) {
//            info.addStringPermission("*:*:*");
//        } else if (user != null) {
//            Set<String> permissions = unitUserService.getPermissionsByUser(user);
//            info.setStringPermissions(permissions);
//        }
//        //方法二： 从subject管理器里获取user
//        //Subject subject = SecurityUtils.getSubject();
//        //User _user = (User) subject.getPrincipal();
//        //System.out.println("subject"+_user.getUsername());
//
        return null;
    }

    //
    // 认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        //验证账号密码
//        CustomLoginToken token = (CustomLoginToken) authenticationToken;
//        UserT user = unitUserService.getUserByUserName(token.getUsername());
//        if (user == null) {
//            return null;
//        }
//        //最后的比对需要交给安全管理器
//        //三个参数进行初步的简单认证信息对象的包装
//        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
//        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt,
//                this.getClass().getSimpleName());
//
        return null;
    }
}
