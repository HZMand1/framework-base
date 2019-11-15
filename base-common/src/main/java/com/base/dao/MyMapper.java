package com.base.dao;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/13 16:29
 * @copyright XXX Copyright (c) 2019
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>, ConditionMapper<T> {

}
