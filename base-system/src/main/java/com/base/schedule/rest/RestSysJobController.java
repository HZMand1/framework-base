package com.base.schedule.rest;

import com.base.schedule.model.SysJobEntity;
import com.base.schedule.service.ISysJobService;
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
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/3/2 12:08
 * @copyright XXX Copyright (c) 2020
 */
@RestController
@RequestMapping("/rest/sys/job/")
@Api(value = "定时任务接口", tags = {"rest-sys-job"})
public class RestSysJobController {

    @Autowired
    private ISysJobService sysJobService;

    @ApiOperation(value = "分页查询全部定时任务")
    @RequestMapping(value = "findUserAllList", method = RequestMethod.POST)
    public AjaxResult findSysJobPageList(@ApiParam(value = "定时任务实体类", required = true) @RequestBody SysJobEntity sysJobEntity) {
        return sysJobService.findSysJobPageList(sysJobEntity);
    }
}
