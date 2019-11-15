package com.base.component.aspect;

import com.base.component.annotation.DataSource;
import com.base.component.annotation.TargetDataSource;
import com.base.datasource.mybatis.DynamicDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/14 15:15
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
@Aspect
@Order(2)
@Component
public class DynamicDataSourceAspect {
    public DynamicDataSourceAspect() {
    }

    @Pointcut("@annotation(targetDataSource)")
    public void dataSourceCut(TargetDataSource targetDataSource) {
    }

    @Before("dataSourceCut(targetDataSource)")
    public void doBefore(JoinPoint point, TargetDataSource targetDataSource) throws Throwable {
        boolean flag = this.changeDataSourceIfHasDataSource(point);
        if (!flag) {
            DynamicDataSource.setDataSourceType(targetDataSource.value());
        }

    }

    @After("dataSourceCut(targetDataSource)")
    public void doAfter(JoinPoint point, TargetDataSource targetDataSource) {
        DynamicDataSource.clearDataSourceType();
    }

    private Boolean changeDataSourceIfHasDataSource(JoinPoint point) throws ClassNotFoundException {
        boolean flag = false;
        String targetName = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] arguments = point.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Method[] var8 = methods;
        int var9 = methods.length;

        for (int var10 = 0; var10 < var9; ++var10) {
            Method method = var8[var10];
            if (method.getName().equals(methodName)) {
                TargetDataSource targetDataSource = (TargetDataSource) method.getAnnotation(TargetDataSource.class);
                if (null != targetDataSource) {
                    Parameter[] parameter = method.getParameters();
                    if (parameter.length == arguments.length) {
                        for (int i = 0; i < parameter.length; ++i) {
                            Annotation an = parameter[i].getAnnotation(DataSource.class);
                            if (null != an) {
                                Object obj = arguments[i];
                                if (null != obj) {
                                    DynamicDataSource.setDataSourceType(String.valueOf(obj));
                                    flag = true;
                                }

                                return flag;
                            }
                        }
                    }
                }
                break;
            }
        }

        return flag;
    }
}

