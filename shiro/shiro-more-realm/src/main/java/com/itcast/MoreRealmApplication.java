package com.itcast;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/5/18 - 16:57
 */
@SpringBootApplication
@MapperScan("com.itcast.dao")
public class MoreRealmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoreRealmApplication.class, args);
    }
}
