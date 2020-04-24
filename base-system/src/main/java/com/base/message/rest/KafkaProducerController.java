package com.base.message.rest;

import com.alibaba.fastjson.JSONObject;
import com.base.message.service.IKafkaMessageService;
import com.base.vo.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/4/2 11:45
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@RestController
@RequestMapping("/rest/kafka/")
public class KafkaProducerController {
    @Autowired
    private IKafkaMessageService kafkaMessageService;

    @RequestMapping(value = "send", method = RequestMethod.POST)
    public AjaxResult send(@RequestBody JSONObject jsonObject) {
        return kafkaMessageService.send(jsonObject);
    }
}
