package com.itcast.shiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述：用户登陆
 *
 * @author JIAQI
 * @date 2020/4/13 - 13:37
 */
@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String loginView() {
        return "/login";
    }

    @PostMapping("/login")
    public String doLogin(String username, String password, boolean rememberMe) {
        // 主体提交认证请求
        Subject currentUser = SecurityUtils.getSubject();
        // 创建token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            // 记住密码设置
            token.setRememberMe(rememberMe);
            // 进行登陆
            currentUser.login(token);
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            log.error("用户不存在");
        } catch (IncorrectCredentialsException e) {
            log.error("用户名/密码错误");
        } catch (LockedAccountException e) {
            log.error("账户被锁定");
        } catch (ExcessiveAttemptsException e) {
            log.error("登录失败多次，账户锁定10分钟");
        } catch (AuthenticationException e) {
            log.error("账户未认证");
        } catch (Exception e) {
            log.error("未知错误信息");
        }
        return "redirect:/login";
    }

    //登录后的首页
    @GetMapping("/index")
    public String loginSuccessView() {
        return "/index";
    }

    //admin角色才能访问
    @RequiresRoles("admin")
    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return "admin success";
    }

    //有save权限才能访问
    @GetMapping("/save")
    @ResponseBody
    @RequiresPermissions("sys:save")
    public String save() {
        return "save success";
    }

    //有edit权限才能访问
    @RequiresPermissions("sys:edit")
    @GetMapping("/edit")
    @ResponseBody
    public String edit() {
        return "edit success";
    }

    //有delete权限才能访问
    @RequiresPermissions("sys:delete")
    @GetMapping("/delete")
    @ResponseBody
    public String delete() {
        return "delete success";
    }

    //未授权错误页面
    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "/unauthorized";
    }
}
