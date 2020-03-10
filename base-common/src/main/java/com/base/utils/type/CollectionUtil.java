package com.base.utils.type;

import java.util.*;

/**TODO  集合工具类
 * @author: 黄芝民
 * @date: 2019年10月22日 下午5:36:14
 * @version V1.0
 * @copyright XXX Copyright (c) 2019
 */
@SuppressWarnings("all")
public class CollectionUtil {

    /** 因为类必须为public，所以只能把构造函数给这样控制 */
    CollectionUtil() {

    }

    public static boolean isEmpty(Collection<?> ars) {
        return ars == null || ars.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> ars) {
        return !isEmpty(ars);
    }

    public static boolean isEmpty(Object[] ars) {
        return ars == null || ars.length == 0;
    }

    public static boolean isNotEmpty(Object[] ars) {
        return !isEmpty(ars);
    }

    public <T> List<T> arrayToList(T... array) {
        return Arrays.asList(array);
    }

    public <T> List<T> collectionToList(Collection<T> collection) {
        return new ArrayList<T>(collection);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] listToArray(List<T> list) {
        return (T[]) list.toArray();
    }

    public String join(Collection<?> collection, String separator) {
        if (isEmpty(collection)) {
            return "";
        } else if (collection.size() == 1) {
            Object obj = collection.iterator().next();
            return obj == null ? "" : obj.toString();
        } else {
            StringBuilder str = new StringBuilder(collection.size() * 50);
            for (Iterator<?> itr = collection.iterator(); itr.hasNext(); ) {
                Object obj = itr.next();
                if (obj != null) {
                    if (itr.hasNext()) {
                        str.append(obj.toString()).append(separator);
                    } else {
                        str.append(obj.toString());
                    }
                }
            }
            return str.toString();
        }
    }

    public String join(Collection<?> collection, char separator) {
        if (isEmpty(collection)) {
            return "";
        } else if (collection.size() == 1) {
            Object obj = collection.iterator().next();
            return obj == null ? "" : obj.toString();
        } else {
            StringBuilder str = new StringBuilder(collection.size() * 50);
            for (Object obj : collection) {
                if (obj != null) {
                    str.append(obj.toString()).append(separator);
                }
            }
            if (str.length() > 0) {
                return str.substring(0, str.length() - 1);
            }
            return "";
        }
    }

}
