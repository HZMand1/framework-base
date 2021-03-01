package com.base.utils.ref;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ObjectUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/14 14:28
 * @copyright XXX Copyright (c) 2019
 */
public abstract class ReflectUtil {

    private static final Logger logger = LogManager.getLogger(ReflectUtil.class);

    public ReflectUtil() {
    }

    /**
     * javaBean转map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> javaBeanToMap(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 获取javaBean的BeanInfo对象
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
        // 获取属性描述器
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            // 获取属性名
            String key = propertyDescriptor.getName();
            // 获取该属性的值
            Method readMethod = propertyDescriptor.getReadMethod();
            // 通过反射来调用javaBean定义的getName()方法
            Object value = readMethod.invoke(obj);
            map.put(key, value);
        }
        return map;
    }

    /**
     * map转javaBean
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T mapToJavaBean(Map<String, Object> map, Class<T> clazz) throws Exception {
        // new 出一个对象
        T obj = clazz.newInstance();
        // 获取javaBean的BeanInfo对象
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz, Object.class);
        // 获取属性描述器
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            // 获取属性名
            String key = propertyDescriptor.getName();
            Object value = map.get(key);
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (map.containsKey(key)) {
                // 解决 argument type mismatch 的问题，转换成对应的javaBean属性类型
                String typeName = propertyDescriptor.getPropertyType().getTypeName();
                // System.out.println(key +"<==>"+ typeName);
                if ("java.lang.Integer".equals(typeName)) {
                    value = Integer.parseInt(value.toString());
                }
                if ("java.lang.Long".equals(typeName)) {
                    value = Long.parseLong(value.toString());
                }
                if ("java.util.Date".equals(typeName)) {
                    value = new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
                }
            }
            // 通过反射来调用javaBean定义的setName()方法
            writeMethod.invoke(obj, value);
        }
        return obj;
    }

    public static <T> String getStringValueByASM(T t, String fieldName) {
        String reStr = null;
        Object getValue = getValueByASM(t, fieldName);
        if (!ObjectUtils.isEmpty(getValue)) {
            reStr = String.valueOf(getValue);
        }

        return reStr;
    }

    public static <T> Object getValueByASM(T t, String fieldName) {
        return MethodAccess.get(t.getClass()).invoke(t, "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Object[0]);
    }

    public static <T> void setValueByASM(T t, String fieldName, Object obj) {
        MethodAccess.get(t.getClass()).invoke(t, "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Object[]{obj});
    }

    public static <T> List<T> listCopyASM(List<?> sourceList, Class<T> targetClazz) {
        List<T> reList = new ArrayList();
        if (!ObjectUtils.isEmpty(sourceList)) {
            sourceList.forEach((source) -> {
                reList.add(objectCopyASM(source, targetClazz));
            });
        }

        return reList;
    }

    public static <T> T objectCopyASM(Object sourceObject, Class<T> targetClazz) {
        Object targetObject = null;

        try {
            targetObject = targetClazz.newInstance();
            if (!ObjectUtils.isEmpty(sourceObject)) {
                List<Field> fieldList = getAllFields(sourceObject);
                if (!ObjectUtils.isEmpty(fieldList)) {
                    Field[] fields = new Field[fieldList.size()];

                    for (int i = 0; i < fieldList.size(); ++i) {
                        fields[i] = (Field) fieldList.get(i);
                    }

                    transFromFieldByASM(sourceObject, targetObject, fields);
                }
            } else {
                logger.debug("func[ReflectUtil.objectCopyASM] - Object is empty");
            }
        } catch (Exception var6) {
            logger.error("func[ReflectUtil.objectCopyASM] Exception [{} - {}] stackTrace[{}] ", new Object[]{var6.getCause(), var6.getMessage(), Arrays.deepToString(var6.getStackTrace())});
        }

        return (T) targetObject;
    }

    public static <T> void objectDataAppendASM(T sourceObject, T targetObject) {
        transFromFieldByASM(sourceObject, targetObject, sourceObject.getClass().getDeclaredFields());
    }

    public static <T> void transFromFieldByASM(Object sourceObject, T targetObject, Field[] fields) {
        String methodName = null;
        String fieldName = null;
        Object getValue = null;
        MethodAccess targetMa = MethodAccess.get(targetObject.getClass());
        MethodAccess sourceMa = MethodAccess.get(sourceObject.getClass());
        Field[] var8 = fields;
        int var9 = fields.length;

        for (int var10 = 0; var10 < var9; ++var10) {
            Field field = var8[var10];

            try {
                field.setAccessible(true);
                fieldName = field.getName();
                methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                getValue = sourceMa.invoke(sourceObject, "get" + methodName, new Object[0]);
                if (!ObjectUtils.isEmpty(getValue)) {
                    targetMa.invoke(targetObject, "set" + methodName, new Object[]{getValue});
                }
            } catch (Exception var13) {
                logger.error("func[ReflectUtil.transFromFieldByASM] Exception [{} - {}] stackTrace[{}] ", new Object[]{var13.getCause(), var13.getMessage(), Arrays.deepToString(var13.getStackTrace())});
            }
        }

    }

    public static <T> List<Field> getAllFields(T t) {
        Class<?> clazz = t.getClass();

        ArrayList fieldList;
        for (fieldList = new ArrayList(); clazz != null; clazz = clazz.getSuperclass()) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }

        List<Field> reList = new ArrayList();
        if (!ObjectUtils.isEmpty(fieldList)) {
            Iterator var4 = fieldList.iterator();

            while (var4.hasNext()) {
                Field field = (Field) var4.next();
                if (!Modifier.isStatic(field.getModifiers())) {
                    reList.add(field);
                }
            }
        }

        return reList;
    }

    public static <T> T map2Bean(Map<String, Object> map, Class<T> targetClazz) {
        Object t = null;

        try {
            t = targetClazz.newInstance();
            Iterator var3 = map.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry) var3.next();
                setValueByASM(t, (String) entry.getKey(), entry.getValue());
            }
        } catch (Exception var5) {
            logger.error("func[ReflectUtil.transFromFieldByASM] Exception [{} - {}] stackTrace[{}] ", new Object[]{var5.getCause(), var5.getMessage(), Arrays.deepToString(var5.getStackTrace())});
        }

        return (T) t;
    }

    public static Map<String, Object> bean2Map(Object bean, String... propNames) {
        Map<String, Object> rtn = new HashMap();
        if (ObjectUtils.isEmpty(propNames)) {
            List<Field> fieldList = getAllFields(bean);
            fieldList.forEach((field) -> {
                Object value = getValueByASM(bean, field.getName());
                rtn.put(field.getName(), value);
            });
        } else {
            String[] var8 = propNames;
            int var4 = propNames.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String propName = var8[var5];
                Object value = getValueByASM(bean, propName);
                rtn.put(propName, value);
            }
        }

        return rtn;
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object... params) throws NoSuchMethodException {
        Class<?>[] types = new Class[params.length];

        for (int i = 0; i < params.length; ++i) {
            types[i] = params[i].getClass();
        }

        Class<?> clazz = object.getClass();
        Method method = null;
        Class superClass = clazz;

        while (superClass != Object.class) {
            try {
                method = superClass.getDeclaredMethod(methodName, types);
                break;
            } catch (NoSuchMethodException var10) {
                logger.error("func[ReflectUtil.invokePrivateMethod] Exception [{} - {}] stackTrace[{}] ", new Object[]{var10.getCause(), var10.getMessage(), Arrays.deepToString(var10.getStackTrace())});
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
                logger.error("func[ReflectUtil.invokePrivateMethod] Exception [{} - {}] stackTrace[{}] ", new Object[]{var9.getCause(), var9.getMessage(), Arrays.deepToString(var9.getStackTrace())});
            }

            method.setAccessible(accessible);
            return result;
        }
    }

}
