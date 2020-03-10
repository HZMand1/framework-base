//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.base.utils.ref;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public SpringUtil() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }

    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        ApplicationContext ac = getApplicationContext();
        Object obj = ac.getBean(name);
        return obj;
    }

    public static <T> T getBean(Class<T> clazz) {
        ApplicationContext ac = getApplicationContext();
        T t = ac.getBean(clazz);
        return t;
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        ApplicationContext ac = getApplicationContext();
        T t = ac.getBean(name, clazz);
        return t;
    }
}
