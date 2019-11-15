package com.base.datasource.redis.impl;

import com.alibaba.fastjson.JSON;
import com.base.datasource.redis.IRedisDataService;
import com.base.datasource.redis.utils.RedisSerializeUtil;
import com.base.utils.type.StringUtil;
import org.apache.ibatis.cache.CacheException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
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
@Service("RedisDataService")
public class RedisDataServiceImpl implements IRedisDataService {
    @Resource
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    public RedisDataServiceImpl() {
    }

    public void setData(final String key, final String value) {
        this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key), RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(value));
                return null;
            }
        });
    }

    public void hSetData(final String Pkey, final String key, final String value) {
        this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hSet(RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(Pkey), RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key), RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(value));
                return null;
            }
        });
    }

    public void hSetByte(final String Pkey, final String key, final Object object) {
        final byte[] objByte = RedisSerializeUtil.serialize(object);
        this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hSet(Pkey.getBytes(), key.getBytes(), objByte);
                return null;
            }
        });
    }

    public void hSetDataByJSON(final String Pkey, final String key, final Object object) {
        final String value = JSON.toJSONString(object);
        this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hSet(RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(Pkey), RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key), RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(value));
                return null;
            }
        });
    }

    public String getData(final String key) {
        return (String)this.redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyByte = RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keyByte)) {
                    byte[] valueByte = connection.get(keyByte);
                    String value = (String)RedisDataServiceImpl.this.redisTemplate.getStringSerializer().deserialize(valueByte);
                    return value;
                } else {
                    return null;
                }
            }
        });
    }

    public Map<String, String> hGetDataByPrimaryKey(final String Pkey) {
        return (Map)this.redisTemplate.execute(new RedisCallback<Map<String, String>>() {
            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                Map<String, String> jsonMap = new HashMap();
                byte[] keyByte = RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(Pkey);
                if (!connection.exists(keyByte)) {
                    return null;
                } else {
                    Map<byte[], byte[]> byteMap = connection.hGetAll(keyByte);
                    Iterator var5 = byteMap.entrySet().iterator();

                    while(var5.hasNext()) {
                        Map.Entry<byte[], byte[]> entry = (Map.Entry)var5.next();
                        String key = (String)RedisDataServiceImpl.this.redisTemplate.getStringSerializer().deserialize((byte[])entry.getKey());
                        String value = (String)RedisDataServiceImpl.this.redisTemplate.getStringSerializer().deserialize((byte[])entry.getValue());
                        jsonMap.put(key, value);
                    }

                    return jsonMap;
                }
            }
        });
    }

    public String hGetData(final String Pkey, final String key) {
        return (String)this.redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] valueByte = connection.hGet(RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(Pkey), RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key));
                String value = (String)RedisDataServiceImpl.this.redisTemplate.getStringSerializer().deserialize(valueByte);
                return !StringUtil.isBlank(value) ? value : null;
            }
        });
    }

    public byte[] hGetByte(final String Pkey, final String key) {
        return (byte[])this.redisTemplate.execute(new RedisCallback<byte[]>() {
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hGet(Pkey.getBytes(), key.getBytes());
            }
        });
    }

    public <T> Object hGetDataByJSON(final String Pkey, final String key, final Class<T> clazz) {
        return this.redisTemplate.execute(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] valueByte = connection.hGet(RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(Pkey), RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key));
                String value = (String)RedisDataServiceImpl.this.redisTemplate.getStringSerializer().deserialize(valueByte);
                return !StringUtil.isBlank(value) ? JSON.parseObject(value, clazz) : null;
            }
        });
    }

    public <T> List<T> hGetDataList(final String Pkey, final String key, final Class<T> clazz) {
        return (List)this.redisTemplate.execute(new RedisCallback<List<T>>() {
            public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] valueByte = connection.hGet(RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(Pkey), RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key));
                String value = (String)RedisDataServiceImpl.this.redisTemplate.getStringSerializer().deserialize(valueByte);
                return !StringUtil.isBlank(value) ? JSON.parseArray(value, clazz) : null;
            }
        });
    }

    public void delete(final String key) {
        this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.del(new byte[][]{RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key)});
                return null;
            }
        });
    }

    public void hDelete(final String Pkey, final String key) {
        this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hDel(RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(Pkey), new byte[][]{RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key)});
                return null;
            }
        });
    }

    public void flushAll() {
        this.redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushAll();
                return null;
            }
        });
    }

    public boolean set(final String key, final int exprieInSecond, final String value) {
        try {
            this.redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    if (exprieInSecond <= 0) {
                        connection.set(RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key), RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(value));
                    } else {
                        connection.setEx(RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(key), (long)exprieInSecond, RedisDataServiceImpl.this.redisTemplate.getStringSerializer().serialize(value));
                    }

                    return null;
                }
            }, true);
            return true;
        } catch (Exception var5) {
            throw new CacheException(var5);
        }
    }
}

