package com.base.schedule.service;

import com.base.schedule.model.SysJobEntity;
import com.base.user.model.UserEntity;
import com.base.vo.AjaxResult;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/3/2 11:48
 * @copyright XXX Copyright (c) 2020
 */
public interface ISysJobService {

    public AjaxResult findSysJobPageList(SysJobEntity sysJobEntity);

    public AjaxResult findSysJobAllList(SysJobEntity sysJobEntity);

    public AjaxResult findSysJobById(SysJobEntity sysJobEntity);

    public AjaxResult insertSysJob(SysJobEntity sysJobEntity);

    public AjaxResult updateSysJobById(SysJobEntity sysJobEntity);

    public AjaxResult delSysJobById(SysJobEntity sysJobEntity);
}
