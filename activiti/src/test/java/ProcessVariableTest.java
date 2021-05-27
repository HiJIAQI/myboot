import com.activiti.demo.ActivitiApplication;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：流程变量测试类
 *
 * @author JIAQI
 * @date 2020/7/17 - 14:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiApplication.class)
public class ProcessVariableTest {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;

    // 启动流程实例并设置流程变量
    @Test
    public void startProcessAndSetVariable() {
        // 流程定义Key
        String processDefinitionKey = "leave";
        // 流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("请假原因", "生病了");
        variables.put("请假天数", 2);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println("流程启动成功:" + processInstance.getId() + "   " + processInstance.getProcessDefinitionId() + "  "
                + processInstance.getProcessInstanceId());
    }

    // 设置流程变量
    @Test
    public void setProcessVariable() {
        // 执行实例id
        String executionId = "17505";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("请假原因", "生病了");
        variables.put("请假天数", 4);
        runtimeService.setVariables(executionId, variables);
        System.out.println("流程变量设置成功");
    }

    // 获取流程变量
    @Test
    public void getProcessVariable() {
        String executionId = "17501";
        String reason = (String) runtimeService.getVariable(executionId, "请假原因");
        Integer day = (Integer) runtimeService.getVariable(executionId, "请假天数");
        System.out.println("请假原因：" + reason);
        System.out.println("请假天数：" + day);
    }

    // 流程变量的支持的类型

    // 查询历史的流程变量
    @Test
    public void getHistoryVariables() {
        String processInstanceId = "17501";
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        for (HistoricVariableInstance hvs : list) {
            System.out.println("ID" + hvs.getId());
            System.out.println("变量值" + hvs.getValue());
            System.out.println("变量名" + hvs.getVariableName());
            System.out.println("变量类型" + hvs.getVariableTypeName());
            System.out.println("#####################");
        }

    }
}