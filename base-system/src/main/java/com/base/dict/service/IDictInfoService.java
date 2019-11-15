package com.base.dict.service;

import com.base.model.DictInfoEntity;
import com.base.vo.AjaxResult;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/13 16:33
 * @copyright XXX Copyright (c) 2019
 */
public interface IDictInfoService {
    public AjaxResult findDictInfoAllList(DictInfoEntity dictInfoEntity);

    public AjaxResult findDictInfoPageList(DictInfoEntity dictInfoEntity);

    public AjaxResult findDictInfoByType(DictInfoEntity dictInfoEntity);

    public AjaxResult insertDictInfo(DictInfoEntity dictInfoEntity);

    public AjaxResult deleteDictInfo(DictInfoEntity dictInfoEntity);

    public AjaxResult updateDictInfo(DictInfoEntity dictInfoEntity);
}
