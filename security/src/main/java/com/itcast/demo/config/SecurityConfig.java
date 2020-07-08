package com.itcast.demo.config;

import com.itcast.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 功能描述：Security配置相关类
 *
 * @author JIAQI
 * @date 2020/6/30 - 10:16
 */
@EnableWebSecurity
// 开启security权限支持
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    /**
     * BCrypt编码加密
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 静态资源设置
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        //不拦截静态资源,所有用户均可访问的资源
        webSecurity.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/images/**"
        );
    }

    /**
     * 用户授权
     * ①：首先通用源码进行分析可知大概的使用方法
     * ②：通过调取请求匹配器【antMatchers】 移除无需过滤的链接
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 首页所有人可以访问  功能需要有权限才能访问
        http.authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated(); // 所有请求都需要认证

        // 登录页
        http.formLogin()  // 表单登录  来身份认证
                .loginPage("/login"); // 自定义登录路径

        // 取消csrf跨站伪造攻击保护
        http.csrf().disable();

        //注销功能
        http.logout().logoutSuccessUrl("/");
    }

    /**
     * 认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

}
