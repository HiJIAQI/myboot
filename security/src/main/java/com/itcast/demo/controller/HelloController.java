package com.itcast.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/6/30 - 10:05
 */
@Controller
public class HelloController {

    /**
     * 主页
     */
    @GetMapping("/")
    public String homeView() {
        return "/home";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/vip1")
    public String vip1View() {
        return "/vip1";
    }

    /**
     * 登陆页
     */
    @GetMapping("/login")
    public String loginView() {
        return "/login";
    }

    @GetMapping("/vip2")
    public String vip2View() {
        return "/vip2";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String view() {
        return "SUCCESS";
    }
}
