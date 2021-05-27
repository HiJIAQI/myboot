package com.itcast.demo.controller;

import cn.util.Result;
import cn.util.enums.ResultEnum;
import com.itcast.demo.entity.SysUser;
import com.itcast.demo.util.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/8/7 - 15:45
 */
@Controller
@RequestMapping("/sys")
public class LoginController {

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    @ResponseBody
    public Result userLogin(HttpServletResponse response, @RequestBody @Valid SysUser sysUser) {
        // 1.从数据库查询用户信息(略)
        // 2.生成Token
        String token = JwtUtils.createJwtToken(sysUser);
        //放到响应头部
        response.setHeader(JwtUtils.TOKEN_HEADER, JwtUtils.TOKEN_PREFIX + token);
        return Result.success(ResultEnum.USER_LOGIN_SUCCESS, token);
    }

    @GetMapping("/hello")
    @ResponseBody
    public Result hello() {
        return Result.success("已经登录的用户可见");
    }

}
