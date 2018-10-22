package com.agent.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author:lixia
 * @description: 获取一个对象的所有属性，包括所有父类的属性
 * @date:Created in 11:32 2017/12/19
 * @modified:
 */
public class FieldUtil {

    /**
     * 获取一个对象的所有属性
     * @param clz 对象class
     * @return List<Field>
     */
    public static List<Field> getAllFields(Class<?> clz){
        return getFields(clz);
    }

    /**
     * 根据属性名称获取属性，包括父类的属性
     * @param clz 对象class
     * @param name 属性名
     * @return
     */
    public static Field getFieldByName(Class<?> clz, String name) {
        return getField(clz, name);
    }

    /**
     * 递归获取对象的所有属性
     * @param clz 对象class
     * @return List<Field>
     */
    private static List<Field> getFields(Class<?> clz){
        List<Field> fields = new ArrayList<Field>();
        if(clz != null){
            fields.addAll(Arrays.asList(clz.getDeclaredFields()));
            fields.addAll(getFields(clz.getSuperclass()));
        }

        return fields;
    }

    /**
     * 递归获取对象的属性
     * @param clz 对象class
     * @return List<Field>
     */
    private static Field getField(Class<?> clz, String name){
        Field field = null;
        if(clz != null){
            try {
                field = clz.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                field = getField(clz.getSuperclass(), name);
            }
        }

        return field;
    }

}
