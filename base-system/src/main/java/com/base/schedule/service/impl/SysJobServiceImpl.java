package com.base.schedule.service.impl;

import com.base.schedule.dao.ISysJobMapper;
import com.base.schedule.model.SysJobEntity;
import com.base.schedule.service.ISysJobService;
import com.base.vo.AjaxResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

import static com.base.enums.BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO;
import static com.base.enums.BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/3/2 11:49
 * @copyright XXX Copyright (c) 2020
 */
@Service
public class SysJobServiceImpl implements ISysJobService {
    @Resource
    private ISysJobMapper sysJobMapper;

    @Override
    public AjaxResult findSysJobPageList(SysJobEntity sysJobEntity) {
        PageHelper.startPage(sysJobEntity.getStart(), sysJobEntity.getPageSize(), "");
        Condition condition = new Condition(SysJobEntity.class);
        List<SysJobEntity> sysJobEntities = sysJobMapper.selectByCondition(condition);
        PageInfo<SysJobEntity> pageInfo = new PageInfo<SysJobEntity>(sysJobEntities);
        return new AjaxResult(AJAX_CODE_YES.value, "条件分页查询", pageInfo);
    }

    @Override
    public AjaxResult findSysJobAllList(SysJobEntity sysJobEntity) {
        Condition condition = new Condition(SysJobEntity.class);
        List<SysJobEntity> sysJobEntities = sysJobMapper.selectByCondition(condition);
        return new AjaxResult(AJAX_CODE_YES.value, "查询全部", sysJobEntities);
    }

    @Override
    public AjaxResult findSysJobById(SysJobEntity sysJobEntity) {
        SysJobEntity sysJobEntity1 = sysJobMapper.selectByPrimaryKey(sysJobEntity);
        return new AjaxResult(AJAX_CODE_YES.value, "根据ID获取数据", sysJobEntity1);
    }

    @Override
    public AjaxResult insertSysJob(SysJobEntity sysJobEntity) {
        int i = sysJobMapper.insert(sysJobEntity);
        if(i > 0){
            return new AjaxResult(AJAX_CODE_YES.value,"新增成功");
        }
        return new AjaxResult(AJAX_CODE_YES.value,"新增失败");
    }

    @Override
    public AjaxResult updateSysJobById(SysJobEntity sysJobEntity) {
        int i = sysJobMapper.updateByPrimaryKeySelective(sysJobEntity);
        if(i > 0){
            return new AjaxResult(AJAX_CODE_YES.value,"修改成功");
        }
        return new AjaxResult(AJAX_CODE_NO.value,"修改失败");
    }

    @Override
    public AjaxResult delSysJobById(SysJobEntity sysJobEntity) {
        int i = sysJobMapper.delete(sysJobEntity);
        if(i > 0){
            return new AjaxResult(AJAX_CODE_YES.value,"删除成功");
        }
        return new AjaxResult(AJAX_CODE_NO.value,"删除失败");
    }
}
