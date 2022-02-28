package com.itcast;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.itcast.mapper")
public class MyCatDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCatDemoApplication.class, args);
	}

}
