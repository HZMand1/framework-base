package com.base.service.impl;

import com.base.enums.BaseEnumCollections;
import com.base.service.EsService;
import com.base.utils.ESUtil;
import com.base.vo.ESGoodsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2021/2/3 13:43
 * @Version V1.0
 * @Copyright  Copyright (c) 2020
 **/
@Slf4j
@RequiredArgsConstructor
@Service("esServiceImpl")
public class EsServiceImpl implements EsService {

    private final ESUtil esUtil;

    @Override
    public List<ESGoodsVo> goodsSearch(ESGoodsVo esGoodsVo) {
        DisMaxQueryBuilder disMaxQueryBuilder = structureQuery(esGoodsVo);
        List<ESGoodsVo> esGoodsVoList = esUtil.search4NoPagination(BaseEnumCollections.GOODS_SEARCH, disMaxQueryBuilder, ESGoodsVo.class);
        return esGoodsVoList;
    }

    /**
     * 中文、拼音混合搜索
     *
     * @param esGoodsVo the content
     * @return dis max query builder
     */
    public DisMaxQueryBuilder structureQuery(ESGoodsVo esGoodsVo) {
        //使用dis_max直接取多个query中，分数最高的那一个query的分数即可
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        //boost 设置权重,只搜索匹配name和disrector字段
        QueryBuilder ikNameQuery = QueryBuilders.matchQuery("goodsName", esGoodsVo.getGoodsName()).boost(2f);
        QueryBuilder pinyinNameQuery = QueryBuilders.matchQuery("goodsName.pinyin", esGoodsVo.getGoodsName());
        QueryBuilder ikDirectorQuery = QueryBuilders.matchQuery("director", esGoodsVo.getGoodsName()).boost(2f);
        disMaxQueryBuilder.add(ikNameQuery);
        disMaxQueryBuilder.add(pinyinNameQuery);
        disMaxQueryBuilder.add(ikDirectorQuery);
        return disMaxQueryBuilder;
    }
}
