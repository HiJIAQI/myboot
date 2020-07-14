//package com.activiti.demo.config;
//
//import org.activiti.engine.ProcessEngineConfiguration;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * 功能描述：activiti配置类
// *
// * @author JIAQI
// * @date 2020/7/9 - 17:09
// */
//@Configuration
//public class activitiConfig {
//
//    @Bean
//    public ProcessEngineConfiguration processEngineConfiguration(@Qualifier("dataSource") DataSource dataSource) {
//
//        //流程配置，与spring整合采用SpringProcessEngineConfiguration这个实现
//        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//        engineConfiguration.setDataSource(dataSource);
//
//        // 配置表初始化方式
//        engineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//        engineConfiguration.setDatabaseType("mysql");
//
//        return engineConfiguration;
//    }
//
//}
