package com.base.utils.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * TODO
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/14 14:28
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2019
 */
public class ObjectUtils {
    public ObjectUtils() {
    }

    public static boolean isNullObj(Map map) {
        return map == null;
    }

    public static boolean isNullObj(List list) {
        return list == null || list.isEmpty() || list.size() == 0;
    }

    public static boolean isNullObj(Object object) {
        return object == null;
    }

    public static boolean isNullObj(String[] args) {
        return args == null || args.length == 0;
    }

    public static byte[] objectToByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException var17) {
            var17.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException var16) {
                    var16.printStackTrace();
                }
            }

            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return bytes;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = ((Comparable)o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });
        Map<K, V> result = new LinkedHashMap();
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Map.Entry<K, V> entry = (Map.Entry)var3.next();
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAscending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = ((Comparable)o1.getValue()).compareTo(o2.getValue());
                return compare;
            }
        });
        Map<K, V> result = new LinkedHashMap();
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Map.Entry<K, V> entry = (Map.Entry)var3.next();
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
