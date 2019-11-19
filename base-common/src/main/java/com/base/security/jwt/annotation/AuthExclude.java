package com.base.security.jwt.annotation;

import java.lang.annotation.*;

/**TODO 排除不需要登录的接口
 * @author 黄芝民
 * @date 2019/10/28 09:58
 * @version V1.0
 * @copyright XXX Copyright (c) 2019
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthExclude {
}
