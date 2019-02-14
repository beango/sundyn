package com.sundyn.helper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class BeanHelper {
    public static <T> T toBean(Map<String, String[]> map, Class<T> clazz) {

        if (map == null || map.size() <= 0) {
            return null;
        }
        T t = null;
        Set<Map.Entry<String, String[]>> entrySet = map.entrySet();
        try {
            t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Map.Entry<String, String[]> entry : entrySet) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    // 属性名
                    String fieldName = field.getName();
                    // 属性类型
                    String fieldType = field.getGenericType().toString();
                    System.out.println("属性名：" + fieldName + ", 属性类型：" + fieldType);
                    if (fieldType.contains("String") && fieldName.equalsIgnoreCase(entry.getKey())) {
                        field.set(t, entry.getValue()[0]);
                    } else if (fieldType.contains("int") && fieldName.equalsIgnoreCase(entry.getKey())) {
                        field.set(t, entry.getValue()[0]);
                    } else if (fieldType.contains("float") && fieldName.equalsIgnoreCase(entry.getKey())) {
                        field.set(t, entry.getValue()[0]);
                    } else if (fieldType.contains("double") && fieldName.equalsIgnoreCase(entry.getKey())) {
                        field.set(t, entry.getValue()[0]);
                    } else if (fieldType.contains("long") && fieldName.equalsIgnoreCase(entry.getKey())) {
                        field.set(t, entry.getValue()[0]);
                    } else if (fieldType.contains("byte") && fieldName.equalsIgnoreCase(entry.getKey())) {
                        field.set(t, entry.getValue()[0]);
                    } else if (fieldType.contains("Integer") && fieldName.equalsIgnoreCase(entry.getKey())) {
                        field.set(t, entry.getValue()[0]);
                    } else if (fieldType.contains("boolean") && fieldName.equalsIgnoreCase(entry.getKey())) {
                        field.set(t, entry.getValue()[0]);
                    }else if (fieldType.contains("Date") && fieldName.equalsIgnoreCase(entry.getKey())) {
                        field.set(t, entry.getValue()[0]);
                    }
                    System.out.println("t= " + t);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return t;
    }
}
