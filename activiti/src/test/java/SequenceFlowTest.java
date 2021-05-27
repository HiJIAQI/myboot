import com.activiti.demo.ActivitiApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：流程连线
 *
 * @author JIAQI
 * @date 2020/7/17 - 16:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiApplication.class)
@Slf4j
public class SequenceFlowTest {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    // 部署流程
    @Test
    public void devProcess() throws IOException {
        String modelId = "80001";
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        if (bytes == null) {
            log.info("部署ID:{}的模型数据为空，请先设计流程并成功保存，再进行发布", modelId);
            return;
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        Deployment deployment = repositoryService.createDeployment()
                // 给流程起一个名字
                .name("领导审批申请")
                // 添加流程图资源文件
                .addBpmnModel(modelData.getKey() + ".bpmn20.xml", model)
                // 部署
                .deploy();
        System.out.println("流程部署成功");
    }

    // 启动任务
    @Test
    public void startProcess() {
        // 流程定义的key
        String processDefinitionKey = "leader";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程启动成功:" + processInstance.getId() + "   " + processInstance.getProcessDefinitionId() + "  "
                + processInstance.getProcessInstanceId());
    }

    // 查询我的个人任务act_ru_task
    @Test
    public void queryMyTask() {
        final List<Task> list = taskService.createTaskQuery()
                .taskAssignee("李四").list();
        for (Task task : list) {
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务办理人:" + task.getAssignee());
            System.out.println("执行实例ID:" + task.getExecutionId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("流程定义ID:" + task.getProcessDefinitionId());
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
            System.out.println("任务创建时间:" + task.getCreateTime());
            System.out.println("####################");

        }
    }

    // 办理任务
    @Test
    public void completeTask() {
        String taskId = "95002";
        taskService.complete(taskId);
        System.out.println("任务办理成功");
    }

    // 办理任务并指定流程走向
    @Test
    public void completeTask2() {
        String taskId = "72505";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("outcome", "2000");
        taskService.complete(taskId, variables);
        System.out.println("任务办理成功");
    }

}
