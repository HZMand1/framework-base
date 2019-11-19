package com.base.security.jwt.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.datasource.redis.IRedisDataService;
import com.base.enums.BaseEnumCollections;
import com.base.security.jwt.annotation.AuthExclude;
import com.base.utils.type.StringUtil;
import com.base.vo.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.List;

/**
 * TODO 权限AOP
 * 用户权限的jwt验证建立在用户已登录的情况，即在使用aop之前用户已经是登录到系统中的
 * 用户生成的Token信息是保存在redis中，并且设置了失效时间。
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/10/28 9:11
 * @copyright XXX Copyright (c) 2019
 */
@Aspect
@Component
public class ControllerAspect {

    private static final long EXPIRE_TIME = 30L * 60;

    public static final String ADMIN = "ADMIN";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private IRedisDataService redisDataService;

    @Pointcut("execution(public * cn.paohe.seed.*.rest.*.*(..)) && !execution(* cn.paohe.seed.*.rest.*.*login(..))")
    public void privilege() {
    }

    /**
     * 校验当前用户的访问权限
     *
     * @param joinPoint
     * @throws Throwable
     */
    @ResponseBody
    @Around("privilege()")
    public Object isAccessMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        //本地请求直接放行
        String remoteAddr = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(remoteAddr) || "127.0.0.1".equals(remoteAddr)) {
            return joinPoint.proceed();
        }
        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        //看是否被排除
        if (targetMethod.isAnnotationPresent(AuthExclude.class)) {
            return joinPoint.proceed();
        }

        //得到方法的访问权限
        final String methodAccess = AnnotationParse.privilegeParse(targetMethod);

        String token = request.getHeader("token");
        if (StringUtil.isBlank(token) || StringUtil.isBlank(redisDataService.getData(token))) {
            response.setStatus(BaseEnumCollections.RestHttpStatus.BAD.value);
            return new AjaxResult(BaseEnumCollections.RestHttpStatus.BAD.value, "请登录……");
        }
        //如果该方法上有权限注解
        if (!StringUtils.isBlank(methodAccess)) {
            List<String> authList = null;//(List<String>) redisUtil.get(token);
            if (authList == null || authList.size() < 1 || (!authList.contains(methodAccess) && !authList.contains(ADMIN))) {
                response.setStatus(BaseEnumCollections.RestHttpStatus.AUTH_ERROR.value);
                return new AjaxResult(BaseEnumCollections.RestHttpStatus.AUTH_ERROR.value, "请求已被拒绝,当前权限不足");
            }
            //刷新过期时间
            refreshRedis(token,JSON.toJSONString(authList));
        }
        return joinPoint.proceed();
    }

    /**
     * TODO 更新过期时间
     *
     * @param token
     * @return
     * @throws
     * @author 夏家鹏
     * @date 2019/11/4 17:47
     */
    private void refreshRedis(String token,String authList) {
        if (token != null) {
            redisDataService.set(token, EXPIRE_TIME,authList);
        }
    }
}
