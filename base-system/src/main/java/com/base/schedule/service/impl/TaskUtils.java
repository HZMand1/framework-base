package com.base.schedule.service.impl;

import cn.uncode.schedule.ConsoleManager;
import cn.uncode.schedule.core.TaskDefine;
import com.alibaba.fastjson.JSON;
import com.base.schedule.model.SysJobEntity;
import com.base.utils.ref.SpringUtil;
import com.base.utils.type.CollectionUtil;
import com.base.utils.type.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**TODO  定时任务处理类
 * @author:      黄芝民
 * @date:        2020年2月17日 下午2:01:30 
 * @version      V1.0   
 * @copyright    XXX Copyright (c) 2020
 */
public class TaskUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskUtils.class);
    
	/**TODO 创建任务
	 * @author:    黄芝民
	 * @date:      2018年5月9日 下午4:02:23 
	 * @throws   
	 */ 
	public static TaskDefine createTaskDefine(SysJobEntity sysJobEntity){
		// 调用任务组名
		if(StringUtil.isBlank(sysJobEntity.getJobGroup())){
			return null;
		}
		// 目标方法
		if(StringUtil.isBlank(sysJobEntity.getInvokeTarget())){
			return null;
		}
		// 定时器表达式
		if(StringUtil.isEmpty(sysJobEntity.getCronExpression())){
        	return null;
        }
		Object targetClass = SpringUtil.getBean(sysJobEntity.getJobGroup());
		if(targetClass == null){
			return null;
		}
		TaskDefine taskDefine = new TaskDefine();
        taskDefine.setTargetBean(sysJobEntity.getJobGroup());
        //判断扫描发送时间在配置文件中是否配置
        taskDefine.setTargetMethod(sysJobEntity.getInvokeTarget());
        taskDefine.setCronExpression(sysJobEntity.getCronExpression());
        taskDefine.setExtKeySuffix(sysJobEntity.getInvokeTarget() + sysJobEntity.getInvokeTarget());
        taskDefine.setParams( JSON.toJSONString(sysJobEntity));
        return taskDefine;
	}
	
	/**TODO 更新定时任务
	 * @param appCode
	 * @param types void
	 * @author:    黄芝民
	 * @date:      2018年5月10日 上午10:14:47 
	 * @throws   
	 */ 
	/*public static void updateTaskDefine(String appCode, List<Integer> types){
		TaskDefine taskDefine = createTaskDefine(appCode, types);
        try {
			if(ConsoleManager.isExistsTask(taskDefine)){
				ConsoleManager.updateScheduleTask(taskDefine);
			}
		} catch (Exception e) {
			logger.error("更新定时任务失败："+ e.getMessage());
		}
	}*/
	
	/**TODO 删除定时任务
	 * @param sysJobEntity
	 * @author:    黄芝民
	 * @date:      2018年5月10日 上午10:20:19 
	 * @throws   
	 */ 
	public static void delTaskDefine(SysJobEntity sysJobEntity){
		List<TaskDefine> ListTask = ConsoleManager.queryScheduleTask();
		if(CollectionUtil.isEmpty(ListTask)){
			return ;
		}
		for (TaskDefine task : ListTask) {
			if(task.getExtKeySuffix().equalsIgnoreCase(sysJobEntity.getJobGroup() + sysJobEntity.getInvokeTarget())){
				ConsoleManager.delScheduleTask(task);
				break ;
			}
		}
	}
	
	public static boolean isExitTask(SysJobEntity sysJobEntity){
		TaskDefine taskDefine = createTaskDefine(sysJobEntity);
		try {
			return ConsoleManager.isExistsTask(taskDefine);
		} catch (Exception e) {
			logger.error("任务是否存在："+ e.getMessage());
			return false;
		}
	}
	
	public static boolean isExitTask(TaskDefine taskDefine){
		try {
			return ConsoleManager.isExistsTask(taskDefine);
		} catch (Exception e) {
			logger.error("任务是否存在："+ e.getMessage());
			return false;
		}
	}

}
