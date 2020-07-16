import com.activiti.demo.ActivitiApplication;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 功能描述：流程定义信息测试类
 *
 * @author JIAQI
 * @date 2020/7/16 - 15:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiApplication.class)
public class ProcessTest {

    @Autowired
    RepositoryService repositoryService;

    // 部署流程
    @Test
    public void processDeploy() {
        // 创建一个部署的构建器
        Deployment deploy = repositoryService.createDeployment()
                // 从类路径中添加资源,一次只能添加一个资源
                .addClasspathResource("processes/leave.bpmn")
                // 设置部署的名称
                .name("请求单流程")
                // 设置部署的类别
                .category("办公类别")
                // 进行部署
                .deploy();
        System.out.println("流程部署成功");

    }

    /**
     * 查看流程部署信息
     */
    @Test
    public void queryDeployProcessInfo() {
        // 部署流程名称
        String deploymentName = "普通请假审批";
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery()
                .deploymentName(deploymentName).list();
        for (Deployment deployment : deploymentList) {
            System.out.println("部署的id:" + deployment.getId());
            System.out.println("部署的名称:" + deployment.getName());
            System.err.println("----------------");
        }
    }

    /**
     * 查询流程定义信息
     */
    @Test
    public void queryProcessInitInfo() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        String key = "leave";
        final List<ProcessDefinition> processDefinitionList = processDefinitionQuery.processDefinitionKey(key).list();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            System.out.println("流程部署对象ID:" + processDefinition.getDeploymentId());
            // 流程定义的key+版本+随机生成数
            System.out.println("流程定义ID:" + processDefinition.getId());
            // 流程定义文件中的name属性值
            System.out.println("流程定义名称:" + processDefinition.getName());
            // 流程定义文件中的id属性值
            System.out.println("流程定义的key:" + processDefinition.getKey());
            // 当流程定义的key值相同的情况下，版本升级，默认从1开始
            System.out.println("流程定义的版本:" + processDefinition.getVersion());
            System.out.println("资源名称bpmn文件:" + processDefinition.getResourceName());
            System.out.println("资源名称png文件:" + processDefinition.getDiagramResourceName());
        }
    }

    /**
     * 删除流程定义信息
     */
    @Test
    public void deleteProcessInitInfo() {
        // 流程部署id
        String deploymentId = "5001";
        repositoryService.deleteDeployment(deploymentId);
        // 当cascade定义为true的时候为级联删除，会删除相关联的流程数据
        // 若不进行级联的话，如果当前有正在执行的流程则会抛出异常，无法进行成功删除
        repositoryService.deleteDeployment(deploymentId, true);
        System.out.println("流程定义信息删除成功");
    }

    // 修改流程定义信息
    // 查询流程图

    /**
     * 附加：查询最新版本流程定义
     */
    @Test
    public void queryLatestProcessDefinition() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        // 查看最新版本的流程定义
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.latestVersion().list();
        for (ProcessDefinition pd : processDefinitionList) {
            System.out.println("流程定义ID:" + pd.getId());
            System.out.println("流程部署ID:" + pd.getDeploymentId());
            System.out.println("流程定义名称:" + pd.getName());
            System.out.println("流程定义的key:" + pd.getKey());
            System.out.println("流程定义的版本:" + pd.getVersion());
            System.out.println("资源名称bpmn文件:" + pd.getResourceName());
            System.out.println("资源名称png文件:" + pd.getDiagramResourceName());
        }
    }

    // 附加：删除流程定义

}
