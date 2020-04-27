package com.base.utils.mymapper;


import com.base.mymapper.MyMapper;
import com.base.utils.mymapper.enums.MapperCriteriaOpTypeEnum;
import org.javatuples.Triplet;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 基础 dao service 本类作为通用mapper的补充,对于一些需要通过condition查询,修改和删除的方法进行封装
 * baseService 中增删改方法查用 select,insert,update,delete等数据库关键字为前缀,
 * 具体业务继承类中使用query,save,modify,remove 等前缀,方便调用的时候方便区分选择
 * 建议单表查询时使用,多表复杂SQL建议使用mybatis xml 写SQL实现,以提升可读性和可维护性
 * @author json
 * @version 1.0
 * @date 2019/7/25 17:45
 */
public abstract class BaseService {

    /**
     * 通用字段,数据有效标识,1=有效，0=逻辑删除,对于线上数据不能轻易删除,使用逻辑删除,建议每个表都加上
     */
    public static String ALIVE_FLAG_KEY = "aliveFlag";

    /**
     * 获取conditionMap 默认放入必要的aliveFlag字段 1=有效，0=逻辑删除
     *
     * @return
     */
    public static Map<String, Object> getConditionMap() {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put(ALIVE_FLAG_KEY, 1);
        return conditionMap;
    }

    /**
     * Triplet<String, Object,Integer> 三元组:第一个参数为字段名,第二个参数为对应的值,第三个参数为条件类型 equal/in/lessThen .....
     * 获取conditionTuple 默认放入必要的aliveFlag字段 1=有效，0=逻辑删除
     *
     * @return
     */
    public static List<Triplet<String, Object, MapperCriteriaOpTypeEnum>> getConditionTuple() {
        ArrayList<Triplet<String, Object, MapperCriteriaOpTypeEnum>> tupleList = new ArrayList<>();
        tupleList.add(Triplet.with(ALIVE_FLAG_KEY, 1, MapperCriteriaOpTypeEnum.EQUAL_TO));
        return tupleList;
    }

    /**
     * 根据传入的class和tuple构建example
     *
     * @param clazz       需要查询的表对应的实体类的class
     * @param tupleList 需要查询的表字段和对应的值,用来做where过滤条件
     * @return
     */
    protected Example buildExampleByTuple(Class clazz, List<Triplet<String, Object,MapperCriteriaOpTypeEnum>> tupleList) {
        Example example = new Condition(clazz);
        initCriteria(tupleList, example);
        return example;
    }

    private void initCriteria(List<Triplet<String, Object, MapperCriteriaOpTypeEnum>> tupleList, Example example) {
        Example.Criteria criteria = example.createCriteria();
        //循环三元祖集合
        for (Triplet<String, Object, MapperCriteriaOpTypeEnum> triplet : tupleList) {
            setCriteria(triplet, criteria);
        }
    }

    protected void setCriteria(Triplet<String, Object, MapperCriteriaOpTypeEnum> triplet, Example.Criteria criteria){
        switch (triplet.getValue2()){
            case EQUAL_TO:
                //value1 尽量保证与数据库类型一致,避免隐式转换
                criteria.andEqualTo(triplet.getValue0(),triplet.getValue1());
                break;
            case NOT_EQUAL_TO:
                //value1 尽量保证与数据库类型一致,避免隐式转换
                criteria.andNotEqualTo(triplet.getValue0(),triplet.getValue1());
                break;
            case IN:
                //value1必须为可迭代类型
                criteria.andIn(triplet.getValue0(), (Iterable) triplet.getValue1());
                break;
            case NOT_IN:
                //value1必须为可迭代类型
                criteria.andNotIn(triplet.getValue0(), (Iterable) triplet.getValue1());
                break;
            case LIKE:
                //value1需为字符串类型
                criteria.andLike(triplet.getValue0(),triplet.getValue1().toString());
                break;
            case NOT_LIKE:
                //value1需为字符串类型
                criteria.andNotLike(triplet.getValue0(),triplet.getValue1().toString());
                break;
            case IS_NULL:
                //value1可为null,不会被用到
                criteria.andIsNull(triplet.getValue0());
                break;
            case IS_NOT_NULL:
                //value1可为null,不会被用到
                criteria.andIsNotNull(triplet.getValue0());
                break;
            case LessThan:
                //value1需为数字类型
                criteria.andLessThan(triplet.getValue0(),triplet.getValue1());
                break;
            case LessThanOrEqualTo:
                //value1需为数字类型
                criteria.andLessThanOrEqualTo(triplet.getValue0(),triplet.getValue1());
                break;
            case GreaterThan:
                //value1需为数字类型
                criteria.andGreaterThan(triplet.getValue0(),triplet.getValue1());
                break;
            case GreaterThanOrEqualTo:
                //value1需为数字类型
                criteria.andGreaterThanOrEqualTo(triplet.getValue0(),triplet.getValue1());
                break;
            case Between:
                //value1需为两个数字类型,用 , 拼成字符串
                String[] betweenData = triplet.getValue1().toString().split(",");
                criteria.andBetween(triplet.getValue0(),betweenData[0],betweenData[1]);
                break;
            case NotBetween:
                //value1需为两个数字类型,用 , 拼成字符串
                String[] notBetweenData = triplet.getValue1().toString().split(",");
                criteria.andNotBetween(triplet.getValue0(),notBetweenData[0],notBetweenData[1]);
                break;
            default:
                //默认为等于
                criteria.andEqualTo(triplet.getValue0(),triplet.getValue1());
                break;
        }
    }


    /**
     * 根据传入的class和map构建example
     *
     * @param clazz       需要查询的表对应的实体类的class
     * @param conditionKV 需要查询的表字段和对应的值,用来做where过滤条件
     * @return
     */
    protected Example buildExampleByMap(Class clazz, Map<String, Object> conditionKV) {
        Example example = new Condition(clazz);
        Example.Criteria criteria = example.createCriteria();
        for (Map.Entry<String, Object> kv : conditionKV.entrySet()) {
            criteria.andEqualTo(kv.getKey(), kv.getValue());
        }
        return example;
    }

    /**
     * 根据传入的class和map构建condition
     *
     * @param clazz       需要查询的表对应的实体类的class
     * @param conditionKV 需要查询的表字段和对应的值,用来做where过滤条件
     * @return
     */
    protected Condition buildEqualToCondition(Class clazz, Map<String, Object> conditionKV) {
        Condition condition = new Condition(clazz);
        Condition.Criteria criteria = condition.createCriteria();
        for (Map.Entry<String, Object> kv : conditionKV.entrySet()) {
            criteria.andEqualTo(kv.getKey(), kv.getValue());
        }
        return condition;
    }


    /**
     * @param clazz       返回的对象
     * @param conditionKV where过滤的字段和对应的值
     * @param myMapper    mapper
     * @param <T>         返回值的泛型
     * @return
     */
    protected <T> T selectOneByCondition(Class<T> clazz, Map<String, Object> conditionKV, MyMapper<T> myMapper) {
        Example example = buildExampleByMap(clazz, conditionKV);
        T instance = myMapper.selectOneByExample(example);
        return instance;
    }

    /**
     * @param clazz       返回的对象
     * @param conditionKV where过滤的字段和对应的值
     * @param myMapper    mapper
     * @param <T>         返回值的泛型
     * @return
     */
    protected <T> List<T> selectListByCondition(Class<T> clazz, Map<String, Object> conditionKV, MyMapper<T> myMapper) {
        Example example = buildExampleByMap(clazz, conditionKV);
        List<T> instanceList = myMapper.selectByExample(example);
        return instanceList;
    }


    /**
     * 根据where条件修改数据()
     *
     * @param t
     * @param conditionKV where条件
     * @param myMapper
     * @param <T>
     * @return
     */
    protected <T> int updateByCondition(T t, Map<String, Object> conditionKV, MyMapper<T> myMapper) {
        Example example = buildExampleByMap(t.getClass(), conditionKV);
        return myMapper.updateByExampleSelective(t, example);
    }


    /**
     * 根据where条件批量修改数据(由于通用mapper暂不支持批量修改,做此封装方法,如果要求性能,推荐使用jdbc的batch操作 )
     *
     * @param tList
     * @param conditionKV where条件
     * @param myMapper
     * @param <T>
     * @return
     */
    protected <T> int updateListByCondition(List<T> tList, Map<String, Object> conditionKV, MyMapper<T> myMapper) {
        int counter = 0;
        if (tList == null || tList.size() == 0) {
            return counter;
        }
        Example example = buildExampleByMap(tList.get(0).getClass(), conditionKV);
        for (T t : tList) {
            counter += myMapper.updateByExampleSelective(t, example);
        }
        return counter;
    }

}
