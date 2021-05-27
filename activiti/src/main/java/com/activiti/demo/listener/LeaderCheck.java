package com.activiti.demo.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 功能描述：领导审批监听
 *
 * @author JIAQI
 * @date 2020/7/21 - 14:37
 */
public class LeaderCheck implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.err.println("##########进入了##########");
        String assignee = "赵六";
        delegateTask.setAssignee(assignee);
    }
}
