package com.base.user.service.impl;

import com.base.user.dao.UserMapper;
import com.base.user.model.UserEntity;
import com.base.user.service.IUserService;
import com.base.vo.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/19 10:45
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public AjaxResult findUserAllList(UserEntity userEntity) {
        return new AjaxResult(userMapper.selectAll());
    }
}
