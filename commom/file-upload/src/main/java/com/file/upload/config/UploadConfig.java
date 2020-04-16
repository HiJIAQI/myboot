//package com.file.upload.config;
//
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.unit.DataSize;
//
//import javax.servlet.MultipartConfigElement;
//
///**
// * 功能描述：文件上传大小配置方法二
// *
// * @author JIAQI
// * @date 2020/4/16 - 9:10
// */
//@Configuration
//public class UploadConfig {
//    /**
//     * 文件上传配置
//     */
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //文件最大
//        factory.setMaxFileSize(DataSize.parse("1MB"));
//        // 设置总上传数据总大小
//        factory.setMaxRequestSize(DataSize.parse("1MB"));
//        return factory.createMultipartConfig();
//    }
//
//}