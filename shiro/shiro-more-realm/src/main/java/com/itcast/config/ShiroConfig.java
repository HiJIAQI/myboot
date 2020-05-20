package com.itcast.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.itcast.realm.AppRealm;
import com.itcast.realm.PcRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

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
    public DefaultWebSecurityManager getDefaulWebSecurityManager(@Qualifier("customModularRealmAuthenticator") CustomModularRealmAuthenticator customModularRealmAuthenticator,
                                                                 @Qualifier("appRealm") AppRealm appRealm,
                                                                 @Qualifier("pcRealm") PcRealm pcRealm,
                                                                 @Qualifier("sessionManager") DefaultSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入自定义的realm 以及 认证器
        securityManager.setAuthenticator(customModularRealmAuthenticator);
        List<Realm> realmList = new ArrayList<>();
        realmList.add(appRealm);
        realmList.add(pcRealm);
        securityManager.setRealms(realmList);
        securityManager.setSessionManager(sessionManager);
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
        factoryBean.setSuccessUrl("/loginSuccess");
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对静态资源设置匿名访问 springboot默认把所有的静态资源都映射到static目录
        filterChainDefinitionMap.put("/login", "anon");//调用登录接口
        //退出登陆路径
        filterChainDefinitionMap.put("/logout", "logout");
        //将所有没有授权的url都设置为需要认证后才能访问(该设置必须放在最后一个Map)
        filterChainDefinitionMap.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }

    @Bean("sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 去掉shiro登录时url里的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 配置使用自定义认证器，可以实现Realm认证，
     * 并且可以指定特定Realm处理特定类型的验证
     */
    @Bean("customModularRealmAuthenticator")
    public CustomModularRealmAuthenticator customModularRealmAuthenticator(@Qualifier("appRealm") AppRealm appRealm,
                                                                           @Qualifier("pcRealm") PcRealm pcRealm) {
        CustomModularRealmAuthenticator realmAuthenticator = new CustomModularRealmAuthenticator();
        // 设置认证策略只验证第一个realm
        realmAuthenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return realmAuthenticator;
    }

    @Bean(name = "appRealm")
    public AppRealm appReaml(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher) {
        AppRealm realm = new AppRealm();
        //加入密码管理
        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        return realm;
    }

    /**
     * 设置密码匹配器  进行加密处理
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        // Shiro自带哈希凭证匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 加密次数
        hashedCredentialsMatcher.setHashIterations(1);
        // 存储散列后的密码是否为16进制
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    @Bean(name = "pcRealm")
    public PcRealm pcRealm(@Qualifier("retryLimitHashedCredentialsMatcher") RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher) {
        PcRealm realm = new PcRealm();
        //加入密码管理
        realm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher);
        return realm;
    }

    /**
     * 自定义密码匹配器
     * 免密登陆使用
     */
    @Bean(name = "retryLimitHashedCredentialsMatcher")
    public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher() {
        // 自定义密码匹配器
        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        retryLimitHashedCredentialsMatcher.setHashAlgorithmName("md5");
        retryLimitHashedCredentialsMatcher.setHashIterations(1);
        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return retryLimitHashedCredentialsMatcher;
    }

    /**
     * 开启注解 Shiro生命周期处理器
     */
    @Bean("lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
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
