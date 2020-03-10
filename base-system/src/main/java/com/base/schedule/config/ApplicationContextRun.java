package com.base.schedule.config;

import cn.uncode.schedule.ConsoleManager;
import cn.uncode.schedule.core.TaskDefine;
import com.alibaba.fastjson.JSON;
import com.base.enums.BaseEnumCollections;
import com.base.schedule.model.SysJobEntity;
import com.base.schedule.service.ISysJobService;
import com.base.schedule.service.impl.TaskUtils;
import com.base.utils.type.CollectionUtil;
import com.base.utils.type.StringUtil;
import com.base.vo.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**TODO  监听Spring启动，添加数据库的开启状态的任务到调度器，并启动后台线程
 * @author:      吕观林
 * @date:        2020年2月17日 下午2:11:05 
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
@Component
public class ApplicationContextRun implements ApplicationRunner {
	
	@Autowired
	private ISysJobService sysTaskService;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextRun.class);

    @Override
	public void run(ApplicationArguments args) throws Exception {
		 logger.info("============springBoot 初始化完成===========");

         // 调度已启动的任务
         try {
             initDbTask();
         } catch (Exception e) {
             logger.error("调度已启动的任务异常！", e);
         }
	}

    /**TODO 调度数据库启动状态的任务
     * @throws Exception void
     * @author:    黄芝民
     * @date:      2018年5月10日 下午6:25:14 
     * @throws   
     */ 
    private void initDbTask() throws Exception {
    	/**查询当前所有接入系统*/
    	SysJobEntity task = new SysJobEntity();
    	AjaxResult result = sysTaskService.findSysJobAllList(task);
    	List<SysJobEntity> sysList = JSON.parseArray(JSON.toJSONString(result.getData()),SysJobEntity.class);
    	if(CollectionUtil.isEmpty(sysList)){
    		return ;
    	}
    	for (SysJobEntity sysJobEntity : sysList) {
    		TaskUtils.delTaskDefine(sysJobEntity);
    		if(StringUtil.equals(sysJobEntity.getStatus(),BaseEnumCollections.YesOrNo.YES.value)){
    			try {
    				TaskDefine taskDefine = TaskUtils.createTaskDefine(sysJobEntity);
    				if(taskDefine != null){
    					ConsoleManager.addScheduleTask(taskDefine);
    				}
    			} catch (Exception e) {
    				logger.error("添加备份任务出错："+e.getMessage());
    			}
    		}
		}
    }
    
}
