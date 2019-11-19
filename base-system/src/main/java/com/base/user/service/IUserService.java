package com.base.user.service;

import com.base.user.model.UserEntity;
import com.base.vo.AjaxResult;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/19 10:45
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public interface IUserService {

    public AjaxResult findUserAllList(UserEntity userEntity);
}
