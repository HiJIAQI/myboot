package com.itcast.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.itcast.shiro.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * 功能描述：shiro配置类
 *
 * @author JIAQI
 * @date 2020/4/13 - 10:58
 */
@Configuration
public class ShiroConfig {

    /**
     * 安全管理器
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaulWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm,
                                                                 @Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 使用自定义的realm
        securityManager.setRealm(userRealm);
        // 使用记住我
        securityManager.setRememberMeManager(cookieRememberMeManager);
        return securityManager;
    }

    /**
     * Shiro过滤器配置
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口
        factoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        factoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        factoryBean.setSuccessUrl("/admin");
        //未认证页面
        factoryBean.setUnauthorizedUrl("/unauthorized");

        // 创建过滤器对相对应的地址进行权限拦截
        /*
         * 自定义url规则 文档：http://shiro.apache.org/web.html#urls-
         * 常用的过滤器
         * anon: 无需 登录就可以访问
         * authc:需要登录才可以访问
         * user:如果使用记住密码功能直接访问
         * perms:该资源必须得到资源权限才可以访问
         * role:必须得到角色权限才可访问
         */

        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对静态资源设置匿名访问 springboot默认把所有的静态资源都映射到static目录
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");//调用登录接口
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/index", "user");
        //退出登陆路径
        filterChainDefinitionMap.put("/logout", "logout");
        //将所有没有授权的url都设置为需要认证后才能访问(该设置必须放在最后一个Map)
        filterChainDefinitionMap.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    /**
     * 开启注解 Shiro生命周期处理器
     */
    @Bean("lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        // Shiro自带加密方式
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();        // 加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 加密次数
        hashedCredentialsMatcher.setHashIterations(1);
        // 存储散列后的密码是否为16进制
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    @Bean(name = "userRealm")
    //@DependsOn("lifecycleBeanPostProcessor")//可选
    //@Dependson指：在另外一个实例创建之后才创建当前实例，也就是，最终两个实例都会创建，只是顺序不一样
    public UserRealm userRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRealm realm = new UserRealm();
        //加入密码管理
        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        return realm;
    }

    /**
     * 开启Shiro的注解支持,需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 配置ShiroDialect,用于theymeleft和Shiro标签的配合使用
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }



    /*##################记住密码##################*/

    /**
     * 记住我操作
     */
    @Bean(name = "cookieRememberMeManager")
    public CookieRememberMeManager getCookieRememberMeManager(@Qualifier("rememberMeCookie") SimpleCookie rememberMeCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie);
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean(name = "rememberMeCookie")
    public SimpleCookie rememberMeCookie() {
        // 实例化Simplecookie构造器 并且设置它的名称
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        // cookie生效时间30天,单位秒;
        cookie.setMaxAge(120);
        return cookie;
    }

    /**
     * FormAuthenticationFilter 过滤器 过滤记住我(可不使用)
     * 相当于你在哪个页面刷新 登陆成功后就跳转到该页面
     */
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter() {
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        //对应前端的checkbox的name = rememberMe
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        return formAuthenticationFilter;
    }

}
