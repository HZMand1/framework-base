package com.base.component.aspect;

import com.base.component.annotation.DataSource;
import com.base.component.annotation.TargetDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Map;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/14 15:09
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public class DataSourceAspect {
    public DataSourceAspect() {
    }

    @Pointcut("execution(* com.base.*.service.impl.*.*(..))")
    public void serviceCut() {
    }

    @Before("serviceCut()")
    public void doBefore(JoinPoint point) throws Throwable {
        String targetName = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] arguments = point.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Method[] var7 = methods;
        int var8 = methods.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            Method method = var7[var9];
            if (method.getName().equals(methodName)) {
                TargetDataSource targetDataSource = (TargetDataSource)method.getAnnotation(TargetDataSource.class);
                if (null != targetDataSource) {
                    Parameter[] parameter = method.getParameters();
                    if (parameter.length == arguments.length) {
                        for(int i = 0; i < parameter.length; ++i) {
                            Annotation an = parameter[i].getAnnotation(DataSource.class);
                            if (null != an) {
                                Object obj = arguments[i];
                                if (null != obj) {
                                    InvocationHandler h = Proxy.getInvocationHandler(targetDataSource);
                                    Field hField = h.getClass().getDeclaredField("memberValues");
                                    hField.setAccessible(true);
                                    Map<String, String> memberValues = (Map)hField.get(h);
                                    memberValues.put("value", String.valueOf(obj));
                                    System.out.println("--------------------");
                                }

                                return;
                            }
                        }
                    }
                }
                break;
            }
        }

    }
}

