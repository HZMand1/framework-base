package com.base.dict.rest;

import com.base.dict.service.IDictInfoService;
import com.base.model.DictInfoEntity;
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
 * @date: 2019/11/13 16:43
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@RestController
@RequestMapping("/rest/sys/dict/")
@Api(value = "数据字典接口", tags = {"rest-seed-outlet-user"})
public class RestDictInfoController {

    @Autowired
    private IDictInfoService dictInfoService;

    @ApiOperation(value = "查询全部数据字典数据")
    @RequestMapping(value = "findDictInfoAllList", method = RequestMethod.POST)
    public AjaxResult findDictInfoAllList(@ApiParam(value = "数据字典实体", required = true) @RequestBody DictInfoEntity dictInfoEntity) {
        return dictInfoService.findDictInfoAllList(dictInfoEntity);
    }

    @ApiOperation(value = "分页查询数据字典数据")
    @RequestMapping(value = "findDictInfoPageList", method = RequestMethod.POST)
    public AjaxResult findDictInfoPageList(@ApiParam(value = "数据字典实体", required = true) @RequestBody DictInfoEntity dictInfoEntity) {
        return dictInfoService.findDictInfoPageList(dictInfoEntity);
    }

    @ApiOperation(value = "新增数据字典数据")
    @RequestMapping(value = "insertDictInfo", method = RequestMethod.POST)
    public AjaxResult insertDictInfo(@ApiParam(value = "数据字典实体", required = true) @RequestBody DictInfoEntity dictInfoEntity) {
        return dictInfoService.insertDictInfo(dictInfoEntity);
    }

    @ApiOperation(value = "修改数据字典数据")
    @RequestMapping(value = "updateDictInfo", method = RequestMethod.POST)
    public AjaxResult updateDictInfo(@ApiParam(value = "数据字典实体", required = true) @RequestBody DictInfoEntity dictInfoEntity) {
        return dictInfoService.updateDictInfo(dictInfoEntity);
    }

    @ApiOperation(value = "删除数据字典数据")
    @RequestMapping(value = "deleteDictInfo", method = RequestMethod.POST)
    public AjaxResult deleteDictInfo(@ApiParam(value = "数据字典实体", required = true) @RequestBody DictInfoEntity dictInfoEntity) {
        return dictInfoService.deleteDictInfo(dictInfoEntity);
    }
}
