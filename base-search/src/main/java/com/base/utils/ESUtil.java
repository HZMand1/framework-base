package com.base.utils;//package cn.paohe.mall.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.base.utils.ref.ReflectUtil;
import com.base.utils.type.CollectionUtil;
import com.base.vo.PageAjax;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2021/1/4 15:53
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@Component
public class ESUtil {
    private static final Logger logger = LogManager.getLogger(ESUtil.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public ESUtil() {
    }

    private <T> String save(String indexName, String typeName, String id, T t) {
        String esId = null;
        if (null != t) {
            try {
                IndexRequest indexRequest = indexRequest = (new IndexRequest(indexName.toLowerCase(), typeName, id.toLowerCase())).source(JSON.toJSON(t), XContentType.JSON);
                IndexResponse indexResponse = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                int counter = indexResponse.getShardInfo().getSuccessful();
                if (counter > 0) {
                    esId = id;
                }
            } catch (IOException var9) {
                logger.error("func[ESUtil.save] Exception [{} - {}] stackTrace[{}] ", new Object[]{var9.getCause(), var9.getMessage(), Arrays.deepToString(var9.getStackTrace())});
            }
        }

        return esId;
    }

    public <T> String save(String indexName, String typeName, T t) {
        String esId = null;
        if (null != t) {
            Field[] fields = t.getClass().getDeclaredFields();
            String id = null;
            Field[] var7 = fields;
            int var8 = fields.length;

            for (int var9 = 0; var9 < var8; ++var9) {
                Field field = var7[var9];
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    id = ReflectUtil.getStringValueByASM(t, field.getName());
                    break;
                }
            }

            if (StringUtils.isNotEmpty(id)) {
                esId = this.save(indexName, typeName, id, t);
            }
        }

        return esId;
    }

    public <T> String save(String indexName, T t) {
        return this.save(indexName, camel2Underline(t.getClass().getSimpleName()).toLowerCase(), t);
    }

    private <T> List<String> bulkSave(String indexName, String typeName, List<T> list) {
        List<String> idList = new ArrayList();
        BulkRequest request = new BulkRequest();
        if (null != list && !list.isEmpty()) {
            try {
                Field[] fields = list.stream().findFirst().get().getClass().getDeclaredFields();
                String fieldName = null;
                Field[] var8 = fields;
                int var9 = fields.length;

                int var10;
                for (var10 = 0; var10 < var9; ++var10) {
                    Field field = var8[var10];
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Id.class)) {
                        fieldName = field.getName();
                        break;
                    }
                }

                if (StringUtils.isNotEmpty(fieldName)) {
                    Iterator var15 = list.iterator();

                    while (var15.hasNext()) {
                        T entity = (T) var15.next();
                        String id = ReflectUtil.getStringValueByASM(entity, fieldName);
                        if (StringUtils.isNotEmpty(id)) {
                            request.add((new IndexRequest(indexName.toLowerCase(), typeName.toLowerCase(), id)).source(JSON.toJSON(entity), XContentType.JSON));
                        }
                    }
                }

                if (!ObjectUtils.isEmpty(request)) {
                    BulkItemResponse[] birs = this.restHighLevelClient.bulk(request, RequestOptions.DEFAULT).getItems();
                    BulkItemResponse[] var18 = birs;
                    var10 = birs.length;

                    for (int var20 = 0; var20 < var10; ++var20) {
                        BulkItemResponse bir = var18[var20];
                        if (!bir.isFailed()) {
                            idList.add(bir.getId());
                        }
                    }
                }
            } catch (JsonProcessingException var13) {
                logger.error("func[ESUtil.bulkSave] Exception [{} - {}] stackTrace[{}] ", new Object[]{var13.getCause(), var13.getMessage(), Arrays.deepToString(var13.getStackTrace())});
            } catch (IOException var14) {
                logger.error("func[ESUtil.bulkSave] Exception [{} - {}] stackTrace[{}] ", new Object[]{var14.getCause(), var14.getMessage(), Arrays.deepToString(var14.getStackTrace())});
            }
        }

        return idList;
    }

    public <T> List<String> bulkSave(String indexName, List<T> list) {
        return this.bulkSave(indexName, camel2Underline(list.stream().findFirst().get().getClass().getSimpleName()).toLowerCase(), list);
    }

    private int delete(String indexName, String typeName, String id) {
        int counter = 0;

        try {
            DeleteRequest deleteRequest = new DeleteRequest(indexName.toLowerCase(), typeName.toLowerCase(), id);
            DeleteResponse deleteResponse = this.restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            counter = deleteResponse.getShardInfo().getSuccessful();
        } catch (IOException var7) {
            logger.error("func[ESUtil.delete] Exception [{} - {}] stackTrace[{}] ", new Object[]{var7.getCause(), var7.getMessage(), Arrays.deepToString(var7.getStackTrace())});
        }

        return counter;
    }

    public <T> int delete(String indexName, T t) {
        int counter = 0;
        if (null != t) {
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            String id = null;
            Field[] var7 = fields;
            int var8 = fields.length;

            for (int var9 = 0; var9 < var8; ++var9) {
                Field field = var7[var9];
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    id = ReflectUtil.getStringValueByASM(t, field.getName());
                    break;
                }
            }

            if (StringUtils.isNotEmpty(id)) {
                counter = this.delete(indexName, camel2Underline(clazz.getSimpleName()).toLowerCase(), id);
            }
        }

        return counter;
    }

    private <T> int bulkDelete(String indexName, String typeName, List<T> list) {
        int counter = 0;
        BulkRequest request = new BulkRequest();
        if (null != list && !list.isEmpty()) {
            Field[] fields = list.stream().findFirst().get().getClass().getDeclaredFields();
            String fieldName = null;
            Field[] var8 = fields;
            int var9 = fields.length;

            for (int var10 = 0; var10 < var9; ++var10) {
                Field field = var8[var10];
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    fieldName = field.getName();
                    break;
                }
            }

            if (StringUtils.isNotEmpty(fieldName)) {
                Iterator var15 = list.iterator();

                while (var15.hasNext()) {
                    T entity = (T) var15.next();
                    String id = ReflectUtil.getStringValueByASM(entity, fieldName);
                    if (StringUtils.isNotEmpty(id)) {
                        request.add(new DeleteRequest(indexName.toLowerCase(), typeName.toLowerCase(), id));
                    }
                }
            }

            if (!ObjectUtils.isEmpty(request)) {
                try {
                    BulkResponse br = this.restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
                    BulkItemResponse[] birs = br.getItems();
                    BulkItemResponse[] var20 = birs;
                    int var21 = birs.length;

                    for (int var12 = 0; var12 < var21; ++var12) {
                        BulkItemResponse bir = var20[var12];
                        if (!bir.isFailed()) {
                            ++counter;
                        }
                    }
                } catch (IOException var14) {
                    logger.error("func[ESUtil.bulkDelete] Exception [{} - {}] stackTrace[{}] ", new Object[]{var14.getCause(), var14.getMessage(), Arrays.deepToString(var14.getStackTrace())});
                }
            }
        }

        return counter;
    }

    public <T> int bulkDelete(String indexName, List<T> list) {
        return this.bulkDelete(indexName, camel2Underline(list.stream().findFirst().get().getClass().getSimpleName()).toLowerCase(), list);
    }

    public <T> List<T> search4NoPagination(String indexName, QueryBuilder queryBuilder, Class<T> clazz) {
        ArrayList list = new ArrayList();

        try {
            List<SearchHit> shList = new ArrayList();
            Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
            SearchRequest searchRequest = new SearchRequest(new String[]{indexName.toLowerCase()});
            searchRequest.scroll(scroll);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(100);
            searchSourceBuilder.query(queryBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            String scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            shList.addAll(Arrays.asList(searchHits));

            while (searchHits != null && searchHits.length > 0) {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = this.restHighLevelClient.searchScroll(scrollRequest, RequestOptions.DEFAULT);
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
                shList.addAll(Arrays.asList(searchHits));
            }

            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            ClearScrollResponse clearScrollResponse = this.restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
            boolean succeeded = clearScrollResponse.isSucceeded();
            if (succeeded && CollectionUtil.isNotEmpty(shList)) {
                Iterator var15 = shList.iterator();

                while (var15.hasNext()) {
                    SearchHit sh = (SearchHit) var15.next();
                    T t = JSON.parseObject(sh.getSourceAsString(), clazz);
                    list.add(t);
                }
            }
        } catch (IOException var18) {
            logger.error("func[ESUtil.search4NoPagination] Exception [{} - {}] stackTrace[{}] ", new Object[]{var18.getCause(), var18.getMessage(), Arrays.deepToString(var18.getStackTrace())});
        }

        return list;
    }

    public <T> PageAjax<T> search4Pagination(String indexName, QueryBuilder queryBuilder, Class<T> clazz, PageAjax<T> page) {
        ArrayList list = new ArrayList();

        try {
            SearchRequest searchRequest = new SearchRequest(new String[]{indexName.toLowerCase()});
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(queryBuilder);
            sourceBuilder.from(page.getPageNo() * page.getPageSize());
            sourceBuilder.size(page.getPageSize());
            sourceBuilder.sort("connectTime", SortOrder.DESC);
            searchRequest.source(sourceBuilder);
            SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = searchResponse.getHits();
            SearchHit[] var10 = searchHits.getHits();
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
                SearchHit sh = var10[var12];
                T t = JSON.parseObject(sh.getSourceAsString(), clazz);
                list.add(t);
            }

            page.setTotal(searchHits.getTotalHits().value);
            page.setRows(list);
        } catch (IOException var15) {
            logger.error("func[ESUtil.search4Pagination] Exception [{} - {}] stackTrace[{}] ", new Object[]{var15.getCause(), var15.getMessage(), Arrays.deepToString(var15.getStackTrace())});
        }
        return page;
    }

    private static String camel2Underline(String line) {
        if (line != null && !"".equals(line)) {
            line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                sb.append(matcher.group().toUpperCase());
                sb.append(matcher.end() == line.length() ? "" : "_");
            }

            return sb.toString();
        } else {
            return "";
        }
    }
}
