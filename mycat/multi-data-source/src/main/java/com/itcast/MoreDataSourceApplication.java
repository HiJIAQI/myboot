package com.itcast;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2021/10/7 - 16:59
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@MapperScan("com.itcast.mapper")
public class MoreDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoreDataSourceApplication.class, args);
    }
}
