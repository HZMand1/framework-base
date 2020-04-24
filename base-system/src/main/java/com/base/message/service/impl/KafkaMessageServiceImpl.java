package com.base.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.base.enums.BaseEnumCollections;
import com.base.message.service.IKafkaMessageService;
import com.base.utils.excetion.FrameworkBaseException;
import com.base.utils.type.ObjectUtils;
import com.base.vo.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/4/2 11:50
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
@Service
public class KafkaMessageServiceImpl implements IKafkaMessageService {

    private final static Logger log = LoggerFactory.getLogger(KafkaMessageServiceImpl.class);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public AjaxResult send(JSONObject jsonObject) {
        try {
            if (ObjectUtils.isNullObj(jsonObject)) {
                return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "消息发送不能为空");
            }
            String topic = jsonObject.getString("topic");
            String message = jsonObject.getString("message");
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    System.out.println("msg OK." + result.toString());
                }

                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("msg send failed: ");
                }
            });
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "消息发送成功", future.get().toString());
        } catch (Exception e) {
            log.error("报错-位置：[OrderServiceImpl->updateUserGetMeal]" + e);
            throw new FrameworkBaseException("报错-位置：[OrderServiceImpl->updateUserGetMeal]" + e.getMessage(), e);
        }
    }
}
