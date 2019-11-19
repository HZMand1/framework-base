package com.base.security.jwt.annotation;

import java.lang.annotation.*;

/**TODO 需要加权限注解
 * @author 黄芝民
 * @date 2019/10/28 09:58
 * @version V1.0
 * @copyright XXX Copyright (c) 2019
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresPermissions {
    String value();
}
