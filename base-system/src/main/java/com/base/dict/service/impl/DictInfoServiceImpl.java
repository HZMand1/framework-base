package com.base.dict.service.impl;

import com.alibaba.fastjson.JSON;
import com.base.component.annotation.TargetDataSource;
import com.base.datasource.redis.IRedisDataService;
import com.base.dict.dao.IDictInfoMapper;
import com.base.dict.service.IDictInfoService;
import com.base.enums.BaseEnumCollections;
import com.base.model.DictInfoEntity;
import com.base.utils.mymapper.BaseService;
import com.base.utils.type.StringUtil;
import com.base.vo.AjaxResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/13 16:34
 * @copyright XXX Copyright (c) 2019
 */
@Service("dictInfoService")
@Transactional(rollbackFor = Exception.class)
public class DictInfoServiceImpl extends BaseService implements IDictInfoService {

    private final static Logger log = LoggerFactory.getLogger(DictInfoServiceImpl.class);

    private final static String REDIS_KEY = "DICT_ID_";

    @Autowired
    private IRedisDataService redisDataService;

    @Resource
    private IDictInfoMapper dictInfoMapper;

    @Override
    @TargetDataSource("base-r")
    public AjaxResult findDictInfoAllList(DictInfoEntity dictInfoEntity) {
        List<DictInfoEntity> infoEntityList = dictInfoMapper.selectAll();
        return new AjaxResult(infoEntityList);
    }

    @Override
    @TargetDataSource("base-r")
    public AjaxResult findDictInfoPageList(DictInfoEntity dictInfoEntity) {
        PageHelper.startPage(dictInfoEntity.getStart(), dictInfoEntity.getPageSize());
        Example example = new Example(dictInfoEntity.getClass());
        List<DictInfoEntity> list = dictInfoMapper.selectByExample(example);
        PageInfo<DictInfoEntity> pageInfo = new PageInfo<DictInfoEntity>(list);
        AjaxResult result = new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据字典信息成功");
        result.setData(pageInfo);
        return result;
    }

    @Override
    @TargetDataSource("base-r")
    public AjaxResult findDictInfoByType(DictInfoEntity dictInfoEntity) {
        if (StringUtil.isBlank(dictInfoEntity.getType())) {
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "类型不能为空。");
        }
        String type = dictInfoEntity.getType();
        String dictInfoRedis = redisDataService.getData(REDIS_KEY + type);
        if (StringUtil.isNotBlank(dictInfoRedis)) {
            log.debug("从Redis中获取数据成功，{}", dictInfoRedis);
            List<DictInfoEntity> infoEntityList = JSON.parseArray(dictInfoRedis, DictInfoEntity.class);
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据字典信息成功",infoEntityList);
        }
        Example example = new Example(dictInfoEntity.getClass());
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", dictInfoEntity.getType());
        List<DictInfoEntity> list = dictInfoMapper.selectByExample(example);
        //存入Redis
        redisDataService.setData(REDIS_KEY + type, JSON.toJSONString(list));
        AjaxResult result = new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "获取数据字典信息成功", list);
        return result;
    }

    @Override
    @TargetDataSource("base-w")
    public AjaxResult insertDictInfo(DictInfoEntity dictInfoEntity) {
        int insertCount = dictInfoMapper.insert(dictInfoEntity);
        if (insertCount > 0) {
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "新增成功");
        }
        return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "新增失败");
    }

    @Override
    @TargetDataSource("base-w")
    public AjaxResult deleteDictInfo(DictInfoEntity dictInfoEntity) {
        int deleteCount = dictInfoMapper.deleteByPrimaryKey(dictInfoEntity);
        if (deleteCount > 0) {
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "删除成功");
        }
        return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "删除失败");
    }

    @Override
    @TargetDataSource("base-w")
    public AjaxResult updateDictInfo(DictInfoEntity dictInfoEntity) {
        int updateCount = dictInfoMapper.updateByPrimaryKeySelective(dictInfoEntity);
        if (updateCount > 0) {
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_YES.value, "修改成功");
        }
        return new AjaxResult(BaseEnumCollections.RestHttpStatus.AJAX_CODE_NO.value, "修改失败");
    }
}
