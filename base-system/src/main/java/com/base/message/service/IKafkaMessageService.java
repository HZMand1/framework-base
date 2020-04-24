package com.base.message.service;

import com.alibaba.fastjson.JSONObject;
import com.base.vo.AjaxResult;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/4/2 11:49
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
public interface IKafkaMessageService {
    AjaxResult send(JSONObject jsonObject);
}
