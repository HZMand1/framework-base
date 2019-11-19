package com.base.user.rest;

import com.base.model.DictInfoEntity;
import com.base.security.jwt.annotation.RequiresPermissions;
import com.base.user.model.UserEntity;
import com.base.user.service.IUserService;
import com.base.vo.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 后台用户接口
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/19 10:17
 * @copyright XXX Copyright (c) 2019
 */
@RestController
@RequestMapping("/rest/sys/user/")
@Api(value = "后台用户接口", tags = {"rest-sys-user"})
public class RestUserController {
    @Autowired
    private IUserService userService;

    @RequiresPermissions("sys:query")
    @ApiOperation(value = "查询全部用户数据")
    @RequestMapping(value = "findUserAllList", method = RequestMethod.POST)
    public AjaxResult findUserAllList(@ApiParam(value = "用户实体", required = true) @RequestBody UserEntity userEntity) {
        return userService.findUserAllList(userEntity);
    }
}
