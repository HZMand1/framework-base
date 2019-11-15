package com.base.datasource.redis;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/15 14:51
 * @copyright XXX Copyright (c) 2019
 */
public interface IRedisDataService {
    void setData(String var1, String var2);

    void hSetData(String var1, String var2, String var3);

    void hSetDataByJSON(String var1, String var2, Object var3);

    String getData(String var1);

    Map<String, String> hGetDataByPrimaryKey(String var1);

    String hGetData(String var1, String var2);

    <T> Object hGetDataByJSON(String var1, String var2, Class<T> var3);

    <T> List<T> hGetDataList(String var1, String var2, Class<T> var3);

    void delete(String var1);

    void hDelete(String var1, String var2);

    boolean set(String var1, int var2, String var3);

    void flushAll();

    void hSetByte(String var1, String var2, Object var3);

    byte[] hGetByte(String var1, String var2);
}