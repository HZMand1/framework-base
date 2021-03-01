package com.base.controller;

import com.base.exception.BaseException;
import com.base.service.EsService;
import com.base.vo.AjaxResult;
import com.base.vo.ESGoodsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2021/3/1 10:39
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/es")
@RequiredArgsConstructor
public class ESController {

    private final EsService esService;

    @PostMapping("goods/search")
    public AjaxResult goodsSearch(@RequestBody ESGoodsVo esGoodsVo) throws BaseException {
        try {
            List<ESGoodsVo> categoryInfoList = esService.goodsSearch(esGoodsVo);
            return new AjaxResult(categoryInfoList);
        } catch (Exception e) {
            throw new BaseException("查询失败");
        }
    }
}
