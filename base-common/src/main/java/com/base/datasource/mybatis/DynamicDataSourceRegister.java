package com.base.datasource.mybatis;

import com.base.datasource.mybatis.model.DataSourceParam;
import com.base.datasource.mybatis.model.DataSourcePoolParam;
import com.base.utils.ref.ReflectUtil;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.validation.DataBinder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/14 14:24
 * @copyright XXX Copyright (c) 2019
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);
    private PropertyValues dataSourcePropertyValues;
    private DataSource defaultDataSource;
    private Map<String, DataSource> dynamicDataSources = new HashMap();
    private static final String headPerfixs = "jdbc";

    public DynamicDataSourceRegister() {
    }

    public void setEnvironment(Environment env) {
        String jdbcDatasources = env.getProperty("jdbc.datasources");
        if (StringUtil.isNotEmpty(jdbcDatasources)) {
            Binder binder = Binder.get(env);
            String[] var4 = jdbcDatasources.split(",");
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String dsPrefix = var4[var6];
                DataSourceParam dsp = (DataSourceParam) binder.bind("jdbc." + dsPrefix, Bindable.of(DataSourceParam.class)).get();
                Map<String, Object> map = null;
                try {
                    map = ReflectUtil.javaBeanToMap(dsp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DataSource ds = this.initDataSource(map);
                if ("master".equals(dsPrefix)) {
                    this.defaultDataSource = ds;
                } else {
                    this.dynamicDataSources.put(dsPrefix, ds);
                }

                try {
                    this.dataBinder(ds, env);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public DataSource initDataSource(Map<String, Object> map) {
        String driverClassName = map.get("driverClassName").toString();
        String url = map.get("url").toString();
        String userAccount = map.get("username").toString();
        String password = map.get("password").toString();
        String dsType = map.get("dsType").toString();
        DataSource dataSource = null;

        try {
            Class<DataSource> dataSourceType = (Class<DataSource>) Class.forName(dsType);
            dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(userAccount).password(password).type(dataSourceType).build();
        } catch (ClassNotFoundException var10) {
            var10.printStackTrace();
        }

        return dataSource;
    }

    private void dataBinder(DataSource dataSource, Environment env) throws Exception {
        Binder binder = Binder.get(env);
        DataBinder dataBinder = new DataBinder(dataSource);
        dataBinder.setIgnoreInvalidFields(false);
        dataBinder.setIgnoreUnknownFields(true);
        if (this.dataSourcePropertyValues == null) {
            DataSourcePoolParam dspp = (DataSourcePoolParam) binder.bind("datasource", Bindable.of(DataSourcePoolParam.class)).get();
            Map<String, Object> values = ReflectUtil.javaBeanToMap(dspp);
            this.dataSourcePropertyValues = new MutablePropertyValues(values);
        }

        dataBinder.bind(this.dataSourcePropertyValues);
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<Object, Object> targetDataSources = new HashMap();
        targetDataSources.put("dataSource", this.defaultDataSource);
        targetDataSources.putAll(this.dynamicDataSources);
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", this.defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);
        logger.debug("多数据源注册成功");
    }
}