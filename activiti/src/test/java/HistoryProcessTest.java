import com.activiti.demo.ActivitiApplication;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 功能描述：历史流程测试类
 *
 * @author JIAQI
 * @date 2020/7/20 - 9:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiApplication.class)
public class HistoryProcessTest {
    @Autowired
    HistoryService historyService;

    // 查看历史任务
    @Test
    public void getHistoryTask() {
        String taskId = "7505";
        final List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee("李四").list();
        for (HistoricTaskInstance task : list) {
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务办理人:" + task.getAssignee());
            System.out.println("执行实例ID:" + task.getExecutionId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("流程定义ID:" + task.getProcessDefinitionId());
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
            System.out.println("任务创建时间:" + task.getCreateTime());
            System.out.println("任务结束时间:" + task.getEndTime());
            System.out.println("#######################################");

        }
    }

    // 查询历史流程变量
    @Test
    public void getHistoryVariables() {
        String processInstanceId = "17501";
        final List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId).list();

        for (HistoricVariableInstance variableInstance : list) {
            System.out.println("流程实例ID：" + variableInstance.getProcessInstanceId());
            System.out.println("流程变量ID：" + variableInstance.getId());
            System.out.println("流程变量名称：" + variableInstance.getVariableName());
            System.out.println("流程变量类型：" + variableInstance.getVariableTypeName());
            System.out.println("流程变量：" + variableInstance.getValue());

        }
    }

}
