package com.base.service;


import com.base.vo.ESGoodsVo;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2021/2/3 13:42
 * @Version V1.0
 * @Copyright  Copyright (c) 2020
 **/
public interface EsService {

    public List<ESGoodsVo> goodsSearch(ESGoodsVo esGoodsVo);
}
