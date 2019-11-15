package com.base.datasource.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/14 14:24
 * @copyright XXX Copyright (c) 2019
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal();

    public DynamicDataSource() {
    }

    protected Object determineCurrentLookupKey() {
        return getDataSourceType();
    }

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return (String)contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
