package com.base.security.jwt.aspect;

import com.base.security.jwt.annotation.RequiresPermissions;

import java.lang.reflect.Method;

/**TODO 得到方法上的权限注解
 * @author: 黄芝民
 * @date: 2019/10/28 9:07 
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public class AnnotationParse {
    /***
     * 解析权限注解
     * @return 返回注解的authorities值
     * @throws Exception
     */
    public static String privilegeParse(Method method) throws Exception {
        //获取该方法
        if (method.isAnnotationPresent(RequiresPermissions.class)) {
            RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
            return annotation.value();
        }
        return null;
    }
}
