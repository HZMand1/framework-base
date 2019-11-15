package com.base.utils.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/12 16:23
 * @copyright XXX Copyright (c) 2019
 */
public class BeanUtil extends BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private BeanUtil() {
    }

    public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
        return getDeclaredField(object.getClass(), propertyName);
    }

    public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
        Class superClass = clazz;

        while(superClass != Object.class) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException var4) {
                superClass = superClass.getSuperclass();
            }
        }

        throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
    }

    public static Object getNameProperty(Object object, String propertyName) {
        Field field = null;

        try {
            field = getDeclaredField(object, propertyName);
        } catch (NoSuchFieldException var7) {
        }

        Object result = null;
        if (null != field) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            try {
                result = field.get(object);
            } catch (Exception var6) {
            }

            field.setAccessible(accessible);
        }

        return result;
    }

    public static Object forceGetProperty(Object object, String propertyName) {
        Object result = null;

        try {
            result = getObjValue(object, propertyName, (Object)null);
        } catch (Exception var4) {
        }

        return result;
    }

    public static void setNameProperty(Object object, String propertyName, Object newValue) throws NoSuchFieldException {
        Field field = getDeclaredField(object, propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        try {
            field.set(object, newValue);
        } catch (Exception var6) {
        }

        field.setAccessible(accessible);
    }

    public static void forceSetProperty(Object object, String propertyName, Object newValue) {
        try {
            setObjValue(object, propertyName, newValue);
        } catch (Exception var4) {
        }

    }

    public static Object invokePrivateMethod(Object object, String methodName, Object... params) throws NoSuchMethodException {
        Class[] types = new Class[params.length];

        for(int i = 0; i < params.length; ++i) {
            types[i] = params[i].getClass();
        }

        Class clazz = object.getClass();
        Method method = null;
        Class superClass = clazz;

        while(superClass != Object.class) {
            try {
                method = superClass.getDeclaredMethod(methodName, types);
                break;
            } catch (NoSuchMethodException var10) {
                superClass = superClass.getSuperclass();
            }
        }

        if (method == null) {
            throw new NoSuchMethodException("No Such Method:" + clazz.getSimpleName() + methodName);
        } else {
            boolean accessible = method.isAccessible();
            method.setAccessible(true);
            Object result = null;

            try {
                result = method.invoke(object, params);
            } catch (Exception var9) {
                ReflectionUtils.handleReflectionException(var9);
            }

            method.setAccessible(accessible);
            return result;
        }
    }

    public static Method transferMethoder(String classpath, String methodname, Class[] types) {
        try {
            Class clazz = Class.forName(classpath);
            return clazz != Object.class ? clazz.getMethod(methodname, types) : null;
        } catch (Exception var5) {
            return null;
        }
    }

    public static Method transferMethoder(Object obj, String methodname, Class[] types) {
        try {
            Class clazz = obj.getClass();
            return clazz != Object.class ? clazz.getMethod(methodname, types) : null;
        } catch (Exception var5) {
            return null;
        }
    }

    public static Field[] getObjProperty(Object obj) {
        Class c = obj.getClass();
        Field[] field = c.getDeclaredFields();
        return field;
    }

    public static void copySupperPropertys(Object arg0, Object arg1) throws Exception {
        if (null != arg0 && null != arg1) {
            Field[] field = getObjSupperProperty(arg1);
            if (null != field) {
                for(int i = 0; i < field.length; ++i) {
                    Object value = forceGetProperty(arg1, field[i].getName());
                    forceSetProperty(arg0, field[i].getName(), value);
                }
            }

        } else {
            throw new Exception("参数为空");
        }
    }

    public static void copyImplPropertys(Object arg0, Object arg1) throws Exception {
        if (null != arg0 && null != arg1) {
            Field[] field = getObjProperty(arg1);
            if (null != field) {
                for(int i = 0; i < field.length; ++i) {
                    Object value = forceGetProperty(arg1, field[i].getName());
                    if (value != null) {
                        forceSetProperty(arg0, field[i].getName(), value);
                    }
                }
            }
        }

    }

    public static Field[] getObjSupperProperty(Object obj) {
        Class c = obj.getClass();
        Class supper = c.getSuperclass();
        List<Field> list = new ArrayList();
        if (null != supper) {
            for(Class superClass = supper; superClass != Object.class; superClass = superClass.getSuperclass()) {
                Field[] fieldchild = superClass.getDeclaredFields();
                if (null != fieldchild) {
                    Field[] var6 = fieldchild;
                    int var7 = fieldchild.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Field field2 = var6[var8];
                        list.add(field2);
                    }
                }
            }
        }

        Field[] field = new Field[list.size()];
        field = (Field[])list.toArray(field);
        return field;
    }

    public static Field[] getObjOpSupperProperty(Object obj) {
        Class c = obj.getClass();
        Class supper = c.getSuperclass();
        List<Field> list = new ArrayList();
        if (null != supper) {
            for(Class superClass = supper; superClass != Object.class; superClass = superClass.getSuperclass()) {
                Field[] fieldchild = superClass.getDeclaredFields();
                if (null != fieldchild) {
                    Field[] var6 = fieldchild;
                    int var7 = fieldchild.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Field field2 = var6[var8];
                        list.add(field2);
                    }
                }
            }
        }

        Field[] field = new Field[list.size()];
        field = (Field[])list.toArray(field);
        return field;
    }

    public static Field[] getObjAllProperty(Object obj) {
        List<Field> list = new ArrayList();

        for(Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fieldchild = superClass.getDeclaredFields();
            if (null != fieldchild) {
                Field[] var4 = fieldchild;
                int var5 = fieldchild.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Field field2 = var4[var6];
                    list.add(field2);
                }
            }
        }

        Field[] field = new Field[list.size()];
        field = (Field[])list.toArray(field);
        return field;
    }

    public static Field[] getObjAllOpProperty(Object obj) {
        List<Field> list = new ArrayList();

        for(Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fieldchild = superClass.getDeclaredFields();
            if (null != fieldchild) {
                Field[] var4 = fieldchild;
                int var5 = fieldchild.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Field field2 = var4[var6];
                    list.add(field2);
                }
            }
        }

        Field[] field = new Field[list.size()];
        field = (Field[])list.toArray(field);
        return field;
    }

    public static String getProNameMethod(String proName) {
        String methodName = "";
        if (StringUtil.isNotBlank(proName)) {
            methodName = "get" + StringUtil.getFirstUpper(proName);
        }

        return methodName;
    }

    public static String getProSetNameMethod(String proName) {
        String methodName = "";
        if (StringUtil.isNotBlank(proName)) {
            methodName = "set" + StringUtil.getFirstUpper(proName);
        }

        return methodName;
    }

    public static Object getObjValue(Object obj, String name, Object defObj) {
        Object valueObj = null;
        String methodName = getProNameMethod(name);
        Method method = transferMethoder(obj, methodName, new Class[0]);
        if (null != method) {
            try {
                valueObj = method.invoke(obj);
                if (null == valueObj) {
                    valueObj = defObj;
                }
            } catch (Exception var7) {
                logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ", var7);
            }
        }

        return valueObj;
    }

    public static void setObjValue(Object obj, String name, Object defObj) {
        String methodName = getProSetNameMethod(name);

        try {
            Field field = getDeclaredField(obj, name);
            Class fclass = field.getType();
            Object valueobj = getValueByType(fclass.getName(), defObj);
            Class[] types = new Class[]{fclass};
            Method method = transferMethoder(obj, methodName, types);
            if (null != method) {
                method.invoke(obj, valueobj);
            }
        } catch (NoSuchFieldException var9) {
        } catch (IllegalArgumentException var10) {
        } catch (IllegalAccessException var11) {
        } catch (InvocationTargetException var12) {
        }

    }

    public static Object getValueByType(String className, Object defObj) {
        Object obj = null;
        if (className.indexOf("String") >= 0) {
            if (defObj instanceof Date) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                obj = formatter.format(defObj);
            } else {
                obj = defObj;
            }
        } else if (className.indexOf("int") >= 0) {
            if (StringUtil.isBlank(String.valueOf(defObj))) {
                defObj = "0";
            }

            if (defObj != null) {
                obj = Long.valueOf(String.valueOf(defObj)).intValue();
            }
        } else if (className.indexOf("Long") >= 0) {
            if (StringUtil.isBlank(String.valueOf(defObj))) {
                defObj = "0";
            }

            if (defObj != null) {
                obj = Long.valueOf(String.valueOf(defObj));
            }
        } else if (className.indexOf("Double") >= 0) {
            if (StringUtil.isBlank(String.valueOf(defObj))) {
                defObj = "0";
            }

            if (defObj != null) {
                obj = Double.valueOf(String.valueOf(defObj));
            }
        } else if (className.indexOf("double") >= 0) {
            if (StringUtil.isBlank(String.valueOf(defObj))) {
                defObj = "0";
            }

            if (defObj != null) {
                obj = Double.valueOf(String.valueOf(defObj));
            }
        } else if (className.indexOf("float") >= 0) {
            if (StringUtil.isBlank(String.valueOf(defObj))) {
                defObj = "0";
            }

            if (defObj != null) {
                obj = Float.valueOf(String.valueOf(defObj));
            }
        } else if (className.indexOf("Float") >= 0) {
            if (StringUtil.isBlank(String.valueOf(defObj))) {
                defObj = "0";
            }

            if (defObj != null) {
                obj = Float.valueOf(String.valueOf(defObj));
            }
        } else if (className.indexOf("Date") >= 0) {
            obj = defObj;
        } else if (className.indexOf("Timestamp") >= 0) {
            obj = defObj;
        } else if (className.indexOf("Integer") >= 0) {
            if (StringUtil.isBlank(String.valueOf(defObj))) {
                defObj = "0";
            }

            if (defObj != null) {
                obj = Integer.valueOf(String.valueOf(defObj));
            }
        } else if (className.indexOf("boolean") >= 0) {
            if (StringUtil.isBlank(String.valueOf(defObj))) {
                defObj = "false";
            }

            if ("true".equals(String.valueOf(defObj))) {
                obj = true;
            } else {
                obj = false;
            }
        } else if (className.indexOf("Boolean") >= 0) {
            if (StringUtil.isBlank(String.valueOf(defObj))) {
                defObj = "false";
            }

            if ("true".equals(String.valueOf(defObj))) {
                obj = true;
            } else {
                obj = false;
            }
        } else {
            obj = defObj;
        }

        return obj;
    }

    public static void setObjValue(Object obj, String name, String defObj) {
        String methodName = getProSetNameMethod(name);

        try {
            Field field = getDeclaredField(obj, name);
            Class fclass = field.getType();
            Class[] types = new Class[]{fclass};
            Method method = transferMethoder(obj, methodName, types);
            if (null != method) {
                method.invoke(obj, getStringToType(fclass, defObj));
            }
        } catch (NoSuchFieldException var8) {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ", var8);
        } catch (IllegalArgumentException var9) {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ", var9);
        } catch (IllegalAccessException var10) {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ", var10);
        } catch (InvocationTargetException var11) {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ", var11);
        }

    }

    public static Object getObject(Object obj, String name, String defObj) {
        try {
            Field field = getDeclaredField(obj, name);
            Class fclass = field.getType();
            return getStringToType(fclass, defObj);
        } catch (Exception var5) {
            return null;
        }
    }

    public static String getObjectHql(Object obj, String name, List<Object> paramlist, Object value) {
        try {
            Field field = getDeclaredField(obj, name);
            Class fclass = field.getType();
            return getStringToHql(fclass, name, paramlist, value);
        } catch (Exception var6) {
            return null;
        }
    }

    public static Object getStringToType(Class typeClass, String value) {
        Object obj = null;
        if (typeClass.equals(String.class)) {
            if (null != value && !StringUtil.isBlank(value)) {
                obj = String.valueOf(value);
            } else {
                obj = "";
            }
        } else if (typeClass.equals(Double.class)) {
            if (null != value && !StringUtil.isBlank(value)) {
                obj = Double.valueOf(value);
            } else {
                obj = 0.0D;
            }
        } else if (typeClass.equals(Integer.class)) {
            if (null != value && !StringUtil.isBlank(value)) {
                obj = Integer.valueOf(value);
            } else {
                obj = 0;
            }
        } else if (typeClass.equals(Date.class)) {
            if (null != value && !StringUtil.isBlank(value)) {
                obj = DateUtil.string2Date(value, "yyyy-MM-dd");
            } else {
                obj = null;
            }
        } else if (typeClass.equals(Long.class)) {
            if (null != value && !StringUtil.isBlank(value)) {
                obj = Long.valueOf(value);
            } else {
                obj = 0L;
            }
        } else {
            obj = 0;
        }

        return obj;
    }

    public static String getStringToHql(Class typeClass, String name, List<Object> paramlist, Object value) {
        String obj = null;
        if (typeClass.equals(String.class)) {
            obj = "'--'";
            paramlist.add(null != value && !"".equals(value) ? value : "--");
        } else if (typeClass.equals(Double.class)) {
            obj = "0";
            paramlist.add(null != value && !"".equals(value) ? value : 0.0D);
        } else if (typeClass.equals(Integer.class)) {
            obj = "0";
            paramlist.add(null != value && !"".equals(value) ? value : 0);
        } else if (typeClass.equals(Date.class)) {
            obj = "to_date('1991.01.01','yyyy.mm.dd')";
            paramlist.add(null != value && !"".equals(value) ? value : new Date("1991.01.01"));
        } else if (typeClass.equals(Long.class)) {
            obj = "0";
            paramlist.add(null != value && !"".equals(value) ? value : 0L);
        } else {
            obj = "0";
            paramlist.add(null != value && !"".equals(value) ? value : 0);
        }

        return obj;
    }

    public static void copyObjectPropertiesToMap(Object arg0, Map<String, Object> paramMap) throws Exception {
        if (arg0 != null && paramMap != null) {
            Field[] field = getObjAllOpProperty(arg0);
            if (field != null) {
                for(int i = 0; i < field.length; ++i) {
                    Object value = forceGetProperty(arg0, field[i].getName());
                    paramMap.put(field[i].getName(), value);
                }
            }

        } else {
            throw new Exception("参数为空");
        }
    }
}