package com.itcast.controller;

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
public class DemoController {

    @GetMapping
    @ResponseBody
    public String demo() {
        return "SUCCESS";
    }
}
