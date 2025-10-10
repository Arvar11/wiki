package com.panda.wiki.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyUtil {

    /**
     * 单体复制
     * @param source 源对象
     * @param clazz 目标类类型
     * @return 复制后的目标对象
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        
        T obj = null;
        try {
            obj = clazz.newInstance();  // Java 8 兼容
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(source, obj);
        return obj;
    }

    /**
     * 列表复制
     * @param source 源列表
     * @param clazz 目标类类型
     * @return 复制后的目标列表
     */
    public static <T> List<T> copyList(List<?> source, Class<T> clazz) {
        List<T> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)) {
            for (Object c : source) {
                T obj = copy(c, clazz);
                target.add(obj);
            }
        }
        return target;
    }
}