import com.activiti.demo.ActivitiApplication;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：用户相关流程
 *
 * @author JIAQI
 * @date 2020/7/21 - 14:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiApplication.class)
public class UserProcessTest {
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;

    // 启动流程
    @Test
    public void startProcess() {
        String processDefinitionKey = "leader";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("username", "张三");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println("流程启动id: " + processInstance.getId());
        System.out.println("流程定义id: " + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id: " + processInstance.getProcessInstanceId());

    }

    // 查看代办任务
    @Test
    public void queryMyTask() {
        String assignee = "张三";
        final List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
        for (Task task : list) {
            System.out.println("任务ID：" + task.getId());
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
        String taskId = "105002";
        taskService.complete(taskId);
        System.out.println("任务办理成功");
    }

    // 查询组任务
    @Test
    public void findGroupTask() {
        String candidateUser = "李四";
        final List<Task> list = taskService.createTaskQuery().taskCandidateUser(candidateUser).list();
        for (Task task : list) {
            System.out.println("任务ID：" + task.getId());
            System.out.println("任务办理人:" + task.getAssignee());
            System.out.println("执行实例ID:" + task.getExecutionId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("流程定义ID:" + task.getProcessDefinitionId());
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
            System.out.println("任务创建时间:" + task.getCreateTime());
            System.out.println("####################");
        }
    }

    // 任务拾取
    @Test
    public void claim() {
        String taskId = "112505";
        String userId = "张三";
        taskService.claim(taskId, userId);
        System.out.println("任务分配成功");
    }

    // 任务回退
    @Test
    public void claimBack() {
        String taskId = "112505";
        taskService.setAssignee(taskId, null);
        System.out.println("任务回退成功");
    }

    // 办理任务
    @Test
    public void complete() {
        String taskId = "117502";
        taskService.complete(taskId);
        System.out.println("任务办理成功");
    }

    // 查询组任务人员列表
    @Test
    public void query() {
        String taskId = "117502";
        final List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink identityLink : identityLinksForTask) {
            System.out.println("组任务成员ID：" + identityLink.getGroupId());
            System.out.println("成员：" + identityLink.getUserId());
            System.out.println("类型：" + identityLink.getType());

        }
    }
}

