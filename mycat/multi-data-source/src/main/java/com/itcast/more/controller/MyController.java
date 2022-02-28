package com.itcast.more.controller;

import com.itcast.more.mapper.db1.Db1Mapper;
import com.itcast.more.mapper.db2.Db2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2021/10/7 - 17:51
 */
@RestController
public class MyController {

    @Autowired
    Db1Mapper db1Mapper;

    @Autowired
    Db2Mapper db2Mapper;

    @GetMapping("/db1")
    public Integer db1() {
        return db1Mapper.countUser();
    }

    @GetMapping("/db2")
    public Integer db2() {
        return db2Mapper.countUser();
    }
}
