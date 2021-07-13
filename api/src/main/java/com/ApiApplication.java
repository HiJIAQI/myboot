package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/6/17 - 9:46
 */
@SpringBootApplication
@MapperScan("com.itcast.dao")
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
