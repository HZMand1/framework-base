package com.base.utils.redis;

import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	/**  
	* @Title: setKey  
	* @Description: TODO(设置一个key) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:34:35   
	* @param @param key
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void setKey(String key,Object value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	/**  
	* @Title: setKey  
	* @Description: TODO(设置一个key带超时) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:34:58   
	* @param @param key
	* @param @param value
	* @param @param timeout    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void setKey(String key,Object value,Long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout);
	}
	
	/**  
	* @Title: getKey  
	* @Description: TODO(获取一个key) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:35:13   
	* @param @param key
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object getKey(String key) {
		if(redisTemplate.hasKey(key)) {
			return redisTemplate.opsForValue().get(key);
		}
		return null;
	}
	
	/**  
	* @Title: hasKey  
	* @Description: TODO(判断一个key是否存在) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:35:24   
	* @param @param key
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws  
	*/  
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}
	
	/**  
	* @Title: getKeyExpire  
	* @Description: TODO(获取一个key的过期时间) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:35:39   
	* @param @param key
	* @param @return    参数  
	* @return long    返回类型  
	* @throws  
	*/  
	public long getKeyExpire(String key) {
		return redisTemplate.getExpire(key);
	}
	
	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}
	
	/**  
	* @Title: getAndSetKey  
	* @Description: TODO(返回旧值并设置新值) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:48:15   
	* @param @param key
	* @param @param value
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object getAndSetKey(String key,Object value) {
		if(redisTemplate.hasKey(key)) {
			return redisTemplate.opsForValue().getAndSet(key, value);
		}
		else {
			redisTemplate.opsForValue().set(key, value);
			return null;
		}
	}
	
	/******************************************String 操作 ***************************************************/
	
	/**  
	* @Title: strlenKey  
	* @Description: TODO(返回长度) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:47:59   
	* @param @param key
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long strlenKey(String key) {
		if(redisTemplate.hasKey(key)) {
			return redisTemplate.opsForValue().size(key);
		}
		return 0L;
	}
	
	/**  
	* @Title: getRangeStringKey  
	* @Description: TODO(返回部分) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:49:01   
	* @param @param key
	* @param @param start
	* @param @param end
	* @param @return    参数  
	* @return String    返回类型  
	* @throws  
	*/  
	public String getRangeStringKey(String key,Long start,Long end) {
		return redisTemplate.opsForValue().get(key, start, end);
	}
	
	/**  
	* @Title: appendStringKey  
	* @Description: TODO(添加子字符串) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:49:11   
	* @param @param key
	* @param @param vlaue
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Integer appendStringKey(String key,String value) {
		return redisTemplate.opsForValue().append(key, value);
	}
	
	/*****************************************************数字操作**************************************/
	
	/**  
	* @Title: setKeyIncrement  
	* @Description: TODO(设置一个key自增长1) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:35:53   
	* @param @param key    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void setKeyIncrement(String key) {
		redisTemplate.opsForValue().increment(key, 1);
	}
	
	/**  
	* @Title: setKeyIncrement  
	* @Description: TODO(设置一个key自增长自定义) 
	* @author edward38ljh
	* @date 2019年6月12日下午1:50:18   
	* @param @param key
	* @param @param ince    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void setKeyIncrement(String key,long ince) {
		redisTemplate.opsForValue().increment(key, ince);
	}
	
	/*****************************************************hash map 操作**********************************************************************/
	
	/**  
	* @Title: hSetHash  
	* @Description: TODO(设置一个key value) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:24:19   
	* @param @param key
	* @param @param hashKey
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void hSetHash(String key,String hashKey,Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}
	
	/**  
	* @Title: hmSetHash  
	* @Description: TODO(设置一个Map) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:24:40   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void hmSetHash(String key,Map values) {
		redisTemplate.opsForHash().putAll(key, values);
	}
	
	/**  
	* @Title: hsetNXHash  
	* @Description: TODO(设置一个key value 仅当key不存在) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:24:59   
	* @param @param key
	* @param @param hashKey
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public boolean hsetNXHash(String key,String hashKey,Object value) {
		return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
	}
	
	/**  
	* @Title: hDelHash  
	* @Description: TODO(删除一个key) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:26:37   
	* @param @param key
	* @param @param hashKey    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void hDelHash(String key,String hashKey) {
		redisTemplate.opsForHash().delete(key, hashKey);
	}
	
	/**  
	* @Title: hDelHash  
	* @Description: TODO(删除多个key) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:27:01   
	* @param @param key
	* @param @param hashKeys    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void hDelHash(String key,String... hashKeys) {
		redisTemplate.opsForHash().delete(key, hashKeys);
	}
	
	/**  
	* @Title: hasKeyHash  
	* @Description: TODO(判断key是否存在) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:27:14   
	* @param @param key
	* @param @param hashKey
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws  
	*/  
	public boolean hasKeyHash(String key,String hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}
	
	/**  
	* @Title: hGetHash  
	* @Description: TODO(获取一个key) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:27:31   
	* @param @param key
	* @param @param hashKey
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object hGetHash(String key,String hashKey) {
		if(redisTemplate.opsForHash().hasKey(key, hashKey)) {
			return redisTemplate.opsForHash().get(key, hashKey);
		}
		return null;
	}
	
	/**  
	* @Title: hmGetHash  
	* @Description: TODO(获取一系列key) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:28:21   
	* @param @param key
	* @param @param hashKeys
	* @param @return    参数  
	* @return List    返回类型  
	* @throws  
	*/  
	public List hmGetHash(String key,List hashKeys) {
		return redisTemplate.opsForHash().multiGet(key, hashKeys);
	}
	
	/**  
	* @Title: hgetAllHash  
	* @Description: TODO(获取整个hash) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:28:44   
	* @param @param key
	* @param @return    参数  
	* @return Map    返回类型  
	* @throws  
	*/  
	public Map hgetAllHash(String key) {
		return redisTemplate.opsForHash().entries(key);
	}
	
	/**  
	* @Title: incrementHash  
	* @Description: TODO(对应key值加一操作) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:28:57   
	* @param @param key
	* @param @param hashKey    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void incrementHash(String key,String hashKey) {
		redisTemplate.opsForHash().increment(key, hashKey, 1);
	}
	
	/**  
	* @Title: incrementHash  
	* @Description: TODO(对应key值加N操作) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:29:13   
	* @param @param key
	* @param @param hashKey
	* @param @param ince    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void incrementHash(String key,String hashKey,Long ince) {
		redisTemplate.opsForHash().increment(key, hashKey, ince);
	}
	
	/**  
	* @Title: incrementFloatHash  
	* @Description: TODO(对应key值重置0然后加一操作) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:29:25   
	* @param @param key
	* @param @param hashKey    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void incrementFloatHash(String key,String hashKey) {
		redisTemplate.opsForHash().put(key, hashKey, 0);
		redisTemplate.opsForHash().increment(key, hashKey, 1);
	}
	
	/**  
	* @Title: hKeysHash  
	* @Description: TODO(获取全部keys) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:30:00   
	* @param @param key
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set hKeysHash(String key) {
		return redisTemplate.opsForHash().keys(key);
	}
	
	/**  
	* @Title: hLenHash  
	* @Description: TODO(获取数量) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:30:12   
	* @param @param key
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long hLenHash(String key) {
		return redisTemplate.opsForHash().size(key);
	}
	
	/**  
	* @Title: hValsHash  
	* @Description: TODO(获取全部values) 
	* @author edward38ljh
	* @date 2019年6月13日上午11:30:25   
	* @param @param key
	* @param @return    参数  
	* @return List    返回类型  
	* @throws  
	*/  
	public List hValsHash(String key) {
		return redisTemplate.opsForHash().values(key);
	}
	
	/******************************************************list 操作*********************************************************************/
	
	/**  
	* @Title: lpushListKey  
	* @Description: TODO(设置lpush一个列表值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:48:48   
	* @param @param key
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void lpushListKey(String key,Object value) {
		redisTemplate.opsForList().leftPush(key, value);
	}
	
	/**  
	* @Title: lpushListKey  
	* @Description: TODO(设置lpush一个列表) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:48:51   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void lpushListKey(String key,List values) {
		redisTemplate.opsForList().leftPushAll(key, values);
	}
	
	/**  
	* @Title: lpushListKey  
	* @Description: TODO(设置lpush一个列表) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:48:53   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void lpushListKey(String key,Object...values) {
		redisTemplate.opsForList().leftPushAll(key, values);
	}
	
	/**  
	* @Title: rpushListKey  
	* @Description: TODO(设置rpush一个列表值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:48:55   
	* @param @param key
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void rpushListKey(String key,Object value) {
		redisTemplate.opsForList().rightPush(key, value);
	}
	
	/**  
	* @Title: rpushListKey  
	* @Description: TODO(设置rpush一个列表) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:48:58   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void rpushListKey(String key,List values) {
		redisTemplate.opsForList().rightPushAll(key, values);
	}
	
	/**  
	* @Title: rpushListKey  
	* @Description: TODO(设置rpush一个列表) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:01   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void rpushListKey(String key,Object...values ) {
		redisTemplate.opsForList().rightPushAll(key, values);
	}
	
	/**  
	* @Title: indexList  
	* @Description: TODO(返回下标值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:03   
	* @param @param key
	* @param @param index
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object indexList(String key,Long index) {
		return redisTemplate.opsForList().index(key, index);
	}
	
	/**  
	* @Title: bLPopList  
	* @Description: TODO(bLPop一个值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:05   
	* @param @param key
	* @param @param timeout
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object bLPopList(String key,Long timeout) {
		return redisTemplate.opsForList().leftPop(key, timeout, TimeUnit.MILLISECONDS);
	}
	
	/**  
	* @Title: bRPopList  
	* @Description: TODO(bRPopp一个值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:08   
	* @param @param key
	* @param @param timeout
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object bRPopList(String key,Long timeout) {
		return redisTemplate.opsForList().rightPop(key, timeout, TimeUnit.MILLISECONDS);
	}
	
	/**  
	* @Title: bRPopLPushList  
	* @Description: TODO(bRPopLPush左进右出一个值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:11   
	* @param @param key
	* @param @param otherKey
	* @param @param timeout
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object bRPopLPushList(String key,String otherKey,Long timeout) {
		return redisTemplate.opsForList().rightPopAndLeftPush(key, otherKey, timeout, TimeUnit.MILLISECONDS);
	}
	
	/**  
	* @Title: lPopList  
	* @Description: TODO(lPop一个值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:13   
	* @param @param key
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object lPopList(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}
	
	/**  
	* @Title: rPopList  
	* @Description: TODO(rPop一个值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:15   
	* @param @param key
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object rPopList(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}
	
	/**  
	* @Title: rPopList  
	* @Description: TODO(RPopLPush左进右出一个值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:18   
	* @param @param key
	* @param @param otherKey
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object rPopLPushList(String key,String otherKey) {
		return redisTemplate.opsForList().rightPopAndLeftPush(key, otherKey);
	}
	
	/**  
	* @Title: lPushXList  
	* @Description: TODO(lPush一个值在最左的地方) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:20   
	* @param @param key
	* @param @param value
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object lPushXList(String key,Object value) {
		return redisTemplate.opsForList().leftPushIfPresent(key, value);
	}
	
	/**  
	* @Title: rPushXList  
	* @Description: TODO(rPush一个值在最右的地方) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:22   
	* @param @param key
	* @param @param value
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object rPushXList(String key,Object value) {
		return redisTemplate.opsForList().rightPushIfPresent(key, value);
	}
	
	/**  
	* @Title: inserBeforeList  
	* @Description: TODO(插入一个值在某个前面) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:25   
	* @param @param key
	* @param @param pivot
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void inserBeforeList(String key,Object pivot,Object value) {
		redisTemplate.opsForList().leftPush(key, pivot, value);
	}
	
	/**  
	* @Title: inserAfterList  
	* @Description: TODO(插入一个值在某个后面) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:27   
	* @param @param key
	* @param @param pivot
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void inserAfterList(String key,Object pivot,Object value) {
		redisTemplate.opsForList().rightPush(key, pivot, value);
	}
	
	/**  
	* @Title: lenList  
	* @Description: TODO(返回list长度) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:30   
	* @param @param key
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long lenList(String key) {
		return redisTemplate.opsForList().size(key);
	}
	
	/**  
	* @Title: lRangeList  
	* @Description: TODO(返回全部列表内容) 
	* @author edward38ljh
	* @date 2019年6月12日下午5:00:36   
	* @param @param key
	* @param @return    参数  
	* @return List    返回类型  
	* @throws  
	*/  
	public List lRangeList(String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}
	
	/**  
	* @Title: lRangeList  
	* @Description: TODO(返回下标start 到end的值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:32   
	* @param @param key
	* @param @param start
	* @param @param end
	* @param @return    参数  
	* @return List    返回类型  
	* @throws  
	*/  
	public List lRangeList(String key,Long start,Long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}
	
	/**  
	* @Title: lRemList  
	* @Description: TODO(删除列表中全部的value值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:36   
	* @param @param key
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void lRemList(String key,Object value) {
		redisTemplate.opsForList().remove(key, 0, value);
	}
	
	/**  
	* @Title: lRemList  
	* @Description: TODO(删除列表中count个的value值) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:40   
	* @param @param key
	* @param @param value
	* @param @param count    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void lRemList(String key,Object value ,Long count) {
		redisTemplate.opsForList().remove(key, count, value);
	}
	
	/**  
	* @Title: setList  
	* @Description: TODO(插入一个值到下标) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:42   
	* @param @param key
	* @param @param index
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void setList(String key,Long index,Object value) {
		redisTemplate.opsForList().set(key, index, value);
	}
	
	/**  
	* @Title: rtimList  
	* @Description: TODO(保留下标 start 到 end) 
	* @author edward38ljh
	* @date 2019年6月12日下午4:49:44   
	* @param @param key
	* @param @param start
	* @param @param end    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void rtimList(String key,Long start,Long end) {
		redisTemplate.opsForList().trim(key, start, end);
	}
	
	/******************************************************set 操作*********************************************************************/
	
	/**  
	* @Title: setSetKey  
	* @Description: TODO(设置一个set) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:17:35   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void setSetKey(String key, Set values) {
		redisTemplate.opsForSet().add(key, values);
	}
	
	/**  
	* @Title: setSetKey  
	* @Description: TODO(设置一个set) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:17:55   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void setSetKey(String key,Object... values ) {
		redisTemplate.opsForSet().add(key, values);
	}
	
	/**  
	* @Title: scardKey  
	* @Description: TODO(返回一个set长度) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:18:07   
	* @param @param key
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long scardKey(String key) {
		return redisTemplate.opsForSet().size(key);
	}
	
	/**  
	* @Title: differenceSet  
	* @Description: TODO(返回两个key set差集) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:18:20   
	* @param @param key
	* @param @param otherKey
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set differenceSet(String key,String otherKey) {
		return redisTemplate.opsForSet().difference(key, otherKey);
	}
	
	/**  
	* @Title: differenceSet  
	* @Description: TODO(两个key set差集 并设置到destKey里面) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:18:48   
	* @param @param key
	* @param @param otherKey
	* @param @param destKey
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long differenceAndStoreSet(String key,String otherKey,String destKey) {
		return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
	}
	
	/**  
	* @Title: intersectSet  
	* @Description: TODO(返回两个key set交集) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:19:12   
	* @param @param key
	* @param @param otherKey
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set intersectSet(String key,String otherKey) {
		return redisTemplate.opsForSet().intersect(key, otherKey);
	}
	
	/**  
	* @Title: intersectSet  
	* @Description: TODO(两个key set交集 并设置到destKey里面) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:19:38   
	* @param @param key
	* @param @param otherKey
	* @param @param destKey
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long intersectAndStoreSet(String key,String otherKey,String destKey) {
		return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
	}
	
	/**  
	* @Title: unionSet  
	* @Description: TODO(返回两个key set并集) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:19:59   
	* @param @param key
	* @param @param otherKey
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set unionSet(String key,String otherKey) {
		return redisTemplate.opsForSet().union(key, otherKey);
	}
		
	/**  
	* @Title: unionSet  
	* @Description: TODO(两个key set并集 并设置到destKey里面) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:20:08   
	* @param @param key
	* @param @param otherKey
	* @param @param destKey
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long unionAndStoreSet(String key,String otherKey,String destKey) {
		return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
	}
	
	/**  
	* @Title: isMemberSet  
	* @Description: TODO(判罚是否存在值) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:20:45   
	* @param @param key
	* @param @param value
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws  
	*/  
	public boolean isMemberSet(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}
	
	/**  
	* @Title: meberSet  
	* @Description: TODO(返回set) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:20:56   
	* @param @param key
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set meberSet(String key) {
		return redisTemplate.opsForSet().members(key);
	}
	
	/**  
	* @Title: popSet  
	* @Description: TODO(弹出一个值) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:21:05   
	* @param @param key
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object popSet(String key) {
		return redisTemplate.opsForSet().pop(key);
	}
	
	/**  
	* @Title: popSetList  
	* @Description: TODO(弹出count个值) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:21:17   
	* @param @param key
	* @param @param count
	* @param @return    参数  
	* @return List    返回类型  
	* @throws  
	*/  
	public List popSetList(String key,Long count) {
		return redisTemplate.opsForSet().pop(key, count);
	}
	
	/**  
	* @Title: sRangeMemberSet  
	* @Description: TODO(返回一个随机值) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:21:44   
	* @param @param key
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws  
	*/  
	public Object sRangeMemberSet(String key) {
		return redisTemplate.opsForSet().randomMember(key);
	}
	
	/**  
	* @Title: sRangeMemberSet  
	* @Description: TODO(返回count个随机值) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:21:57   
	* @param @param key
	* @param @param count
	* @param @return    参数  
	* @return List    返回类型  
	* @throws  
	*/  
	public List sRangeMemberSet(String key,Long count) {
		return redisTemplate.opsForSet().randomMembers(key, count);
	}
	
	/**  
	* @Title: moveSet  
	* @Description: TODO(吧一个set里面的值移动到另外一个set) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:22:10   
	* @param @param key
	* @param @param value
	* @param @param destKey    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void moveSet(String key,Object value,String destKey) {
		redisTemplate.opsForSet().move(key, value, destKey);
	}
	
	/**  
	* @Title: sremSet  
	* @Description: TODO(删除一个值) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:22:28   
	* @param @param key
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void sremSet(String key,Object value) {
		redisTemplate.opsForSet().remove(key, value);
	}
	
	/**  
	* @Title: sremSet  
	* @Description: TODO(删除批量值) 
	* @author edward38ljh
	* @date 2019年6月12日下午2:22:39   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void sremSet(String key,Object... values) {
		redisTemplate.opsForSet().remove(key, values);
	}
	
	/***************************************************sorted Set 操作*****************************************************************************/
	
	/**  
	* @Title: addzSet  
	* @Description: TODO(添加一个带权重得值) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:28:25   
	* @param @param key
	* @param @param value
	* @param @param score    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void addzSet(String key,Object value,Double score) {
		redisTemplate.opsForZSet().add(key, value, score);
	}
	
	/**  
	* @Title: addzSet  
	* @Description: TODO(添加一系列带权重得值) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:30:54   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void addzSet(String key,Set<TypedTuple<Object>> values) {
		redisTemplate.opsForZSet().add(key, values);
	}
	
	/**  
	* @Title: zcardZSet  
	* @Description: TODO(返回基数) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:31:08   
	* @param @param key
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long zcardZSet(String key) {
		return redisTemplate.opsForZSet().size(key);
	}
	
	/**  
	* @Title: zcountZSet  
	* @Description: TODO(返回权重在min至max得成员个数) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:31:40   
	* @param @param key
	* @param @param max
	* @param @param min
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long zcountZSet(String key,Double max,Double min) {
		return redisTemplate.opsForZSet().count(key, min, max);
	}
	
	/**  
	* @Title: zIncrementZSet  
	* @Description: TODO(给value得权重加1) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:32:32   
	* @param @param key
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void zIncrementZSet(String key,Object value) {
		redisTemplate.opsForZSet().incrementScore(key, value, 1);
	}
	
	/**  
	* @Title: zIncrementZSet  
	* @Description: TODO(给value得权重加N) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:32:53   
	* @param @param key
	* @param @param value
	* @param @param score    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void zIncrementZSet(String key,Object value,Double score) {
		redisTemplate.opsForZSet().incrementScore(key, value, score);
	}
	
	/**  
	* @Title: zRangeZSet  
	* @Description: TODO(返回全部 权重顺序) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:34:04   
	* @param @param key
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set zRangeZSet(String key) {
		return redisTemplate.opsForZSet().range(key, 0, -1);
	}
	
	/**  
	* @Title: zRangeZSet  
	* @Description: TODO(从下标start到end 权重顺序) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:33:08   
	* @param @param key
	* @param @param start
	* @param @param end
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set zRangeZSet(String key,Long start,Long end) {
		return redisTemplate.opsForZSet().range(key, start, end);
	}
	
	/**  
	* @Title: zRangeByScoreZSet  
	* @Description: TODO(返回权重在min到max之间得set 权重顺序) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:34:53   
	* @param @param key
	* @param @param max
	* @param @param min
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set zRangeByScoreZSet(String key,Double max,Double min) {
		return redisTemplate.opsForZSet().rangeByScore(key, min, max);
	}
	
	/**  
	* @Title: zRangeByLexZSet  
	* @Description: TODO(返回值范围内得set 权重顺序) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:36:15   
	* @param @param key
	* @param @param range
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set zRangeByLexZSet(String key, Range range) {
		return redisTemplate.opsForZSet().rangeByLex(key, range);
	}
	
	/**  
	* @Title: zRankZset  
	* @Description: TODO(返回value得排名) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:37:16   
	* @param @param key
	* @param @param value
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long zRankZset(String key,Object value) {
		return redisTemplate.opsForZSet().rank(key, value);
	}
	
	/**  
	* @Title: zRemZSet  
	* @Description: TODO(删除value) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:37:55   
	* @param @param key
	* @param @param value    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void zRemZSet(String key,Object value) {
		redisTemplate.opsForZSet().remove(key, value);
	}
	
	/**  
	* @Title: zRemZSet  
	* @Description: TODO(批量删除values) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:38:18   
	* @param @param key
	* @param @param values    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void zRemZSet(String key,Object... values) {
		redisTemplate.opsForZSet().remove(key, values);
	}
	
	/**  
	* @Title: zRemRangeByRankZSet  
	* @Description: TODO(删除下标从start 到end) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:38:30   
	* @param @param key
	* @param @param start
	* @param @param end
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long zRemRangeByRankZSet(String key,Long start,Long end) {
		return redisTemplate.opsForZSet().removeRange(key, start, end);
	}
	
	/**  
	* @Title: zRemRangeByScoreZSet  
	* @Description: TODO(删除权重min到max) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:38:54   
	* @param @param key
	* @param @param max
	* @param @param min
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long zRemRangeByScoreZSet(String key,Double max,Double min) {
		return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
	}
	
	/**  
	* @Title: zReverseRangeZSet  
	* @Description: TODO(返回全部，权重倒序) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:41:15   
	* @param @param key
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set zReverseRangeZSet(String key) {
		return redisTemplate.opsForZSet().reverseRange(key, 0, -1);
	}
	
	/**  
	* @Title: zReverseRangeZSet  
	* @Description: TODO(返回下标start 到end，权重倒序) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:39:09   
	* @param @param key
	* @param @param start
	* @param @param end
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set zReverseRangeZSet(String key,Long start,Long end) {
		return redisTemplate.opsForZSet().reverseRange(key, start, end);
	}
	
	/**  
	* @Title: zReverseRangeByScoreZSet  
	* @Description: TODO(返回权重min到max，权重倒序) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:40:53   
	* @param @param key
	* @param @param max
	* @param @param min
	* @param @return    参数  
	* @return Set    返回类型  
	* @throws  
	*/  
	public Set zReverseRangeByScoreZSet(String key,Double max,Double min) {
		return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
	}
	
	/**  
	* @Title: zReverseRankZSet  
	* @Description: TODO(返回value得倒序排名) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:48:50   
	* @param @param key
	* @param @param value
	* @param @return    参数  
	* @return Long    返回类型  
	* @throws  
	*/  
	public Long zReverseRankZSet(String key,Object value) {
		return redisTemplate.opsForZSet().reverseRank(key, value);
	}
	
	/**  
	* @Title: zScoreZSet  
	* @Description: TODO(返回一个value得权重) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:49:25   
	* @param @param key
	* @param @param value
	* @param @return    参数  
	* @return Double    返回类型  
	* @throws  
	*/  
	public Double zScoreZSet(String key,Object value) {
		return redisTemplate.opsForZSet().score(key, value);
	}
	
	/**  
	* @Title: zUnionStoreZSet  
	* @Description: TODO(两个set得并集存到destKey) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:49:44   
	* @param @param key
	* @param @param otherKeys
	* @param @param destKey    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void zUnionStoreZSet(String key,String otherKeys,String destKey) {
		redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
	}
	
	/**  
	* @Title: zUnionStoreZSet  
	* @Description: TODO(两个set得并集存到destKey)  
	* @author edward38ljh
	* @date 2019年6月13日下午12:52:38   
	* @param @param key
	* @param @param values
	* @param @param destKey    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void zUnionStoreZSet(String key,Set values,String destKey) {
		redisTemplate.opsForZSet().unionAndStore(key, values, destKey);
	}
	
	/**  
	* @Title: zInterStoreZSet  
	* @Description: TODO(两个set得交集存到destKey) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:52:45   
	* @param @param key
	* @param @param otherKey
	* @param @param destKey    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void zInterStoreZSet(String key,String otherKey,String destKey) {
		redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
	}
	
	/**  
	* @Title: zInterStoreZSet  
	* @Description: TODO(两个set得交集存到destKey) 
	* @author edward38ljh
	* @date 2019年6月13日下午12:53:57   
	* @param @param key
	* @param @param values
	* @param @param destKey    参数  
	* @return void    返回类型  
	* @throws  
	*/  
	public void zInterStoreZSet(String key,Set values,String destKey) {
		redisTemplate.opsForZSet().intersectAndStore(key, values, destKey);
	}

	/***********************************************************/

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}
	
}
