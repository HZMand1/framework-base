package com.base.datasource.mybatis.model;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/14 14:25
 * @copyright XXX Copyright (c) 2019
 */
public class DataSourceParam {
    private String dsType;
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public DataSourceParam() {
    }

    public String getDsType() {
        return this.dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
