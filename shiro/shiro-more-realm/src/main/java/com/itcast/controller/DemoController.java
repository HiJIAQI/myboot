package com.itcast.controller;

import com.itcast.config.CustomLoginToken;
import com.itcast.config.LoginType;
import com.itcast.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/5/18 - 16:55
 */
@Controller
@Slf4j
public class DemoController {

//    @GetMapping("/login")
//    @ResponseBody
//    public String login() {
//        return "登陆页面";
//    }

    @GetMapping("/login")
    public String login(SysUser sysUser) {
        // 主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
        // 创建token
        CustomLoginToken token = new CustomLoginToken(sysUser.getUserName(), sysUser.getPassword(), LoginType.PC_USER);
        try {
            subject.login(token);
            return "redirect:/loginSuccess";
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
        return "redirect:/loginFail";
    }

    @GetMapping("/index")
    @ResponseBody
    @RequiresRoles("admin")
    public String index() {
        return "主页";
    }

    @GetMapping("/loginSuccess")
    @ResponseBody
    public String loginSuccess() {
        return "登陆成功页面";
    }

    @GetMapping("/loginFail")
    @ResponseBody
    public String loginFail() {
        return "登陆失败页面";
    }
}
