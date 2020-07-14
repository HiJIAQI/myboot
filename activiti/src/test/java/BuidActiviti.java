//import com.activiti.demo.ActivitiApplication;
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.ProcessEngineConfiguration;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.repository.Deployment;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * 功能描述：
// *
// * @author JIAQI
// * @date 2020/7/9 - 17:24
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ActivitiApplication.class)
//public class BuidActiviti {
//    private ProcessEngine processEngine = null;
//
//    @Test
//    public void buidActivitiTable() {
//        // 连接数据库
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl("jdbc:mysql://47.102.115.140:3306/demo_activiti?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&nullCatalogMeansCurrent=true");
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//
//        // 创建流程引擎配置，与spring整合采用SpringProcessEngineConfiguration这个实现
//        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//        engineConfiguration.setDataSource(dataSource);
//
//        // 设置创建表的策略 （当没有表时，自动创建表）
//        //  DB_SCHEMA_UPDATE_FALSE = "false"; //不会自动创建表，没有表，则抛异常
//        //  DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop"; //先删除，再创建表
//        //  DB_SCHEMA_UPDATE_TRUE = "true"; //假如没有表，则自动创建
//        engineConfiguration.setDatabaseSchemaUpdate("true");
//        engineConfiguration.setDatabaseType("mysql");
//
//        //  创建表结构
//        processEngine = engineConfiguration.buildProcessEngine();
//        System.out.println("流程引擎创建成功!");
//    }
//
//    // 部署流程
//    @Test
//    public void deployProcess() {
//        buidActivitiTable();
//        //获取仓库服务 ：管理流程定义
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Deployment deploy = repositoryService.createDeployment()    // 创建一个部署的构建器
//                .addClasspathResource("LeaveActiviti.bpmn") // 从类路径中添加资源,一次只能添加一个资源
//                .name("请求单流程")  // 设置部署的名称
//                .category("办公类别")   // 设置部署的类别
//                .deploy();
//
//        System.out.println("部署的id" + deploy.getId());
//        System.out.println("部署的名称" + deploy.getName());
//    }
//
//    // 启动流程
//    // 查询流程
//    // 完成流程
//}
