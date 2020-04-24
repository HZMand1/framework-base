package com.base.message.config;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/4/23 16:55
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用代码创建的topic
 * 三个参数意思：topic的名称；分区数量，新主题的复制因子；如果指定了副本分配，则为-1。
 */
@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic batchTopic() {
        NewTopic newTopic = new NewTopic("testTopic", 10, (short) 1);
        System.out.println(newTopic.toString());
        return newTopic;
    }
}
