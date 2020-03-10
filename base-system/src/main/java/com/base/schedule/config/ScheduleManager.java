package com.base.schedule.config;

import cn.uncode.schedule.ZKScheduleManager;
import cn.uncode.schedule.zk.ZKManager;
import com.base.utils.type.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**TODO  schedule配置
 * @author:      黄芝民
 * @date:        2020年2月17日 下午2:01:44 
 * @version      V1.0   
 * @copyright    XXX Copyright (c) 2020
 */
@Configuration
public class ScheduleManager {
	
	   private static final Logger logger = LoggerFactory.getLogger(ScheduleManager.class);

	    /**
	     * 执行器所用的zookeeper地址和端口
	     */
	   @Value("${zookeeper.address}")
	   private String zkConnect;

	    /**
	     * 调度器对应的跟节点
	     */
	   @Value("${uncode.schedule.rootPath:/uncode/schedules}")
	    private String rootPath;

	    /**
	     * session超时时间
	     */
	    @Value("${uncode.schedule.zkSessionTimeout:6000}")
	    private int zkSessionTimeout;

	    @Value("${uncode.schedule.zkUsername:}")
	    private String zkUsername;

	    @Value("${uncode.schedule.zkPassword:}")
	    private String zkPassword;

	    private List<String> ipBlackList;

	    /**
	     * 获取配置
	     *
	     * @return
	     */
	    private Map<String, String> getConfig() {
	        Map<String, String> properties = new HashMap<String, String>();
	        properties.put(ZKManager.KEYS.zkConnectString.key, zkConnect);
	        if (StringUtil.isNotBlank(rootPath)) {
	            properties.put(ZKManager.KEYS.rootPath.key, rootPath);
	        }
	        if (zkSessionTimeout > 0) {
	            properties.put(ZKManager.KEYS.zkSessionTimeout.key, zkSessionTimeout + "");
	        }
	        if (StringUtil.isNotBlank(zkUsername)) {
	            properties.put(ZKManager.KEYS.userName.key, zkUsername);
	        }
	        if (StringUtil.isNotBlank(zkPassword)) {
	            properties.put(ZKManager.KEYS.password.key, zkPassword);
	        }
	        properties.put("autoRegisterTask", "true");
	        StringBuilder sb = new StringBuilder();
	        if (ipBlackList != null && ipBlackList.size() > 0) {
	            for (String ip : ipBlackList) {
	                sb.append(ip).append(",");
	            }
	            ipBlackList.remove(sb.lastIndexOf(","));
	        }
	        properties.put(ZKManager.KEYS.ipBlacklist.key, sb.toString());
	        return properties;
	    }


	    /**
	     * 初始化zkScheduleManager
	     *
	     * @return
	     */
	    @Bean(name = "zkScheduleManager", initMethod = "init")
	    public ZKScheduleManager commonMapper() {
	        ZKScheduleManager zkScheduleManager = new ZKScheduleManager();
	        zkScheduleManager.setZkConfig(getConfig());
	        logger.info("ZKScheduleManager initializing...");
	        return zkScheduleManager;
	    }
	    
	    public String getRootPath() {
	        return rootPath;
	    }

}
