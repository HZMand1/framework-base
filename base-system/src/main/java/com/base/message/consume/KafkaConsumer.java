package com.base.message.consume;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/4/2 11:47
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Component
public class KafkaConsumer {
    private final static Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "testTopic")
    public void listen(ConsumerRecord<?, ?> record) throws Exception {
        log.info("{} - {}:{}", record.topic(), record.key(), record.value());
        record.topic();
    }
}
