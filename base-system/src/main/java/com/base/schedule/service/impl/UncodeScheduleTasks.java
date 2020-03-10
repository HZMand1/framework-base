package com.base.schedule.service.impl;

import org.springframework.stereotype.Component;

@Component
public class UncodeScheduleTasks {

    /**
     * TODO 定时任务实现例子
     *
     * @param taskParam 定时任务参数
     * @throws
     * @author: 吕观林
     * @date: 2020年2月20日 下午3:06:12
     */
    public void taskExample(String taskParam) {
        System.out.println("定时任务启用:" + taskParam);
    }
}
